package com.hoverse.backend.service.impl;

import com.hoverse.backend.config.GeminiConfig;
import com.hoverse.backend.dto.gemini.GeminiRequestDTO;
import com.hoverse.backend.dto.gemini.GeminiResponseDTO;
import com.hoverse.backend.dto.gemini.GeminiSearchConditionResponseDTO;
import com.hoverse.backend.dto.gemini.PlaceContextRequestDTO;
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

    public static final String PROMPT = "Bạn là một hệ thống trích xuất dữ liệu (Data Extractor) phục vụ cho một API.\n" +
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
    private final PlaceRepository placeRepository;

    private String cleanJsonString(String rawJson){
        if(rawJson == null) return "{}";
        return rawJson.replace("```json", "")
                .replace("```","")
                .trim();
    }

    @Override
    public String recommendPlaces(String userRequirement) {
        return null;
    }

    @Override
    public GeminiSearchConditionResponseDTO extractSearchConditions(String userRequirement) {
        String finalPrompt = String.format(PROMPT, userRequirement);

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
        log.info("🎯 Điều kiện AI bóc tách: {}", conditions);

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

        log.info("🔍 DB trả về {} ứng viên tiềm năng.", candidates.size());

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
}
