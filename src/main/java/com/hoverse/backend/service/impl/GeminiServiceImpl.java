package com.hoverse.backend.service.impl;

import com.hoverse.backend.config.GeminiConfig;
import com.hoverse.backend.dto.gemini.*;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.specification.AIPlaceSpecification;
import com.hoverse.backend.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    private final GeminiConfig geminiConfig;
    private final RestClient restClient = RestClient.create();
    private final ObjectMapper objectMapper;

    public static final String PROMPT_CALL_1 = "Bạn là một hệ thống trích xuất dữ liệu (Data Extractor) phục vụ cho một API.\n" +
            "Nhiệm vụ của bạn là đọc câu nói của người dùng và trích xuất thông tin thành định dạng JSON chính xác.\n" +
            "\n" +
            "LUẬT BẮT BUỘC (NẾU VI PHẠM HỆ THỐNG SẼ BỊ LỖI):\n" +
            "1. CHỈ trả về duy nhất chuỗi JSON.\n" +
            "2. KHÔNG ĐƯỢC có lời chào, giải thích hay bất kỳ chữ nào khác.\n" +
            "3. KHÔNG ĐƯỢC bọc kết quả trong markdown (Tuyệt đối KHÔNG dùng ```json hay ```). Trả về JSON thô.\n" +
            "\n" +
            "Cấu trúc JSON yêu cầu:\n" +
            "{\n" +
            "  \"category\": \"Loại hình. CHỈ ĐƯỢC PHÉP trả về 1 trong các slug sau: [cafe, restaurant, food, bar-pub, hotel, tourist-attraction, park, entertainment, shopping, cinema, museum, culture-history, sport, nightlife, religious, education, health, service, transport]. Trả về null nếu không rõ.\",\n" +
            "  \"location\": \"Quận/Huyện tại TP.HCM (Ví dụ: Gò Vấp, Quận 1). Trả về null nếu người dùng không đề cập.\",\n" +
            "  \"tags\": [\"Danh sách các tính từ chỉ tiện ích, không gian (Ví dụ: yên tĩnh, view đẹp, học nhóm)\"]\n" +
            "}\n" +
            "\n" +
            "Câu của người dùng: \"%s\"";

    public static final String PROMPT_CALL_2 = "Bạn là một chuyên gia tư vấn địa điểm. " +
            "Người dùng yêu cầu: \"%s\".\n" +
            "Dưới đây là danh sách tối đa 15 địa điểm tiềm năng (định dạng JSON):\n%s\n\n" +
            "LUẬT BẮT BUỘC:\n" +
            "1. HÃY ĐỌC THẬT KỸ tags và description của từng quán. CHỈ chọn tối đa 3 địa điểm phù hợp nhất với yêu cầu của user.\n" +
            "2. CHỈ ĐƯỢC CHỌN ID CÓ TRONG DANH SÁCH. TUYỆT ĐỐI KHÔNG TỰ BỊA ĐỊA ĐIỂM.\n" +
            "3. TRẢ VỀ DUY NHẤT MẢNG JSON, KHÔNG DÙNG MARKDOWN, KHÔNG GIẢI THÍCH THÊM.\n" +
            "Cấu trúc JSON bắt buộc:\n" +
            "[\n" +
            "  {\n" +
            "    \"placeId\": (ID của địa điểm),\n" +
            "    \"reason\": \"(Giải thích 1 câu ngắn gọn tại sao quán này hợp với yêu cầu của user)\"\n" +
            "  }\n" +
            "]";
    private final PlaceRepository placeRepository;

    private String cleanJsonString(String rawJson){
        if(rawJson == null) return "{}";
        return rawJson.replace("```json", "")
                .replace("```","")
                .trim();
    }

    @Override
    public GeminiSearchConditionResponseDTO extractSearchConditions(String userRequirement) {
        String finalPrompt = String.format(PROMPT_CALL_1, userRequirement);

        GeminiRequestDTO requestDTO = GeminiRequestDTO.builder()
                .contents(List.of(
                        GeminiRequestDTO.GeminiContent.builder()
                                .parts(List.of(
                                        GeminiRequestDTO.GeminiPart.builder()
                                                .text(finalPrompt)
                                                .build()
                                )).build()
                )).build();

        String urlWithKey = geminiConfig.getUrl()
                + "/models/" + geminiConfig.getModel()
                + ":" + geminiConfig.getMethod()
                + "?key=" + geminiConfig.getKey();

        GeminiResponseDTO responseDTO = restClient.post()
                .uri(urlWithKey)
                .body(requestDTO)
                .retrieve()
                .body(GeminiResponseDTO.class);

        if(responseDTO != null && responseDTO.getCandidates() != null && !responseDTO.getCandidates().isEmpty()){
            String rawText = responseDTO.getCandidates().get(0).getContent().getParts().get(0).getText();

            String cleanJson = cleanJsonString(rawText);

            try {
                return objectMapper.readValue(cleanJson, GeminiSearchConditionResponseDTO.class);
            } catch (Exception e) {
                return new GeminiSearchConditionResponseDTO();
            }
        }

        return new GeminiSearchConditionResponseDTO();
    }

    @Override
    public List<PlaceContextRequestDTO> processRecommendation(String userPrompt) {
        GeminiSearchConditionResponseDTO conditions = extractSearchConditions(userPrompt);

        Specification<Place> spec = Specification
                .where(AIPlaceSpecification.hasActiveStatus());

        if(conditions.getLocation() != null){
            spec = spec.and(AIPlaceSpecification.matchesLocation(conditions.getLocation()));
        }

        if(conditions.getCategory() != null){
            spec = spec.and(AIPlaceSpecification.matchesCategorySlug(conditions.getCategory()));
        }

        Pageable limit = PageRequest.of(0,15);
        Page<Place> candidatePage = placeRepository.findAll(spec, limit);
        List<Place> candidates = candidatePage.getContent();

        if(candidates.isEmpty()){
            return new ArrayList<>();
        }

        List<PlaceContextRequestDTO> contextList =  candidates.stream().map(place->{
            List<String> tagNames = place.getTags().stream()
                    .map(tag->tag.getName())
                    .collect(Collectors.toList());

            return PlaceContextRequestDTO.builder()
                    .id(place.getId())
                    .title(place.getTitle())
                    .address(place.getAddress())
                    .description(place.getDescription())
                    .tags(tagNames)
                    .build();
        }).collect(Collectors.toList());

        return contextList;
    }

    @Override
    public List<GeminiRecommendResponseDTO> recommendPlaces(String userRequirement) {
        try {
            List<PlaceContextRequestDTO> candidates = processRecommendation(userRequirement);

            if(candidates.isEmpty()){
                return new ArrayList<>();
            }

            String candidatesJson = objectMapper.writeValueAsString(candidates);

            String finalPrompt = String.format(PROMPT_CALL_2, userRequirement, candidatesJson);
            GeminiRequestDTO requestDTO = GeminiRequestDTO.builder()
                    .contents(List.of(
                            GeminiRequestDTO.GeminiContent.builder()
                                    .parts(List.of(
                                            GeminiRequestDTO.GeminiPart.builder()
                                                    .text(finalPrompt)
                                                    .build()
                                    ))
                                    .build()
                    ))
                    .build();

            String urlWithKey = geminiConfig.getUrl()
                    + "/models/" + geminiConfig.getModel()
                    + ":" + geminiConfig.getMethod()
                    + "?key=" + geminiConfig.getKey();

            GeminiResponseDTO responseDTO = restClient.post()
                    .uri(urlWithKey)
                    .body(requestDTO)
                    .retrieve()
                    .body(GeminiResponseDTO.class);

            if(responseDTO != null &&  responseDTO.getCandidates() != null && !responseDTO.getCandidates().isEmpty()){
                String rawText = responseDTO.getCandidates().get(0).getContent().getParts().get(0).getText();
                String cleanText =  cleanJsonString(rawText);

                List<GeminiRankingResponseDTO> aiChoices = objectMapper.readValue(cleanText,
                        new TypeReference<List<GeminiRankingResponseDTO>>() {});

                List<GeminiRecommendResponseDTO> finalResults = new ArrayList<>();
                for(GeminiRankingResponseDTO choice: aiChoices){
                    candidates.stream()
                            .filter(cand->cand.getId().equals(choice.getPlaceId()))
                            .findFirst()
                            .ifPresent(matchedContext->{
                                finalResults.add(GeminiRecommendResponseDTO.builder()
                                        .placeContextRequestDTO(matchedContext)
                                        .reason(choice.getReason())
                                        .build());
                            });
                }
                return finalResults;
            }
        } catch (Exception e) {
            throw new RuntimeException("Có lõi xảy ra ở luồng call AI lần 2: "+e);
        }
        return null;
    }
}
