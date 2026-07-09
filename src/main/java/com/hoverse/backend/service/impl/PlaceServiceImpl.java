package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.CloudinaryUploadResponseDTO;
import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.entity.Category;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.PlaceMedia;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.CategoryRepository;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.repository.specification.PlaceSpecification;
import com.hoverse.backend.service.CloudinaryService;
import com.hoverse.backend.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PlaceMapper placeMapper;
    private final CloudinaryService cloudinaryService;

    private PlaceMedia toEntity (CloudinaryUploadResponseDTO responseDTO, Place place){
        return PlaceMedia.builder()
                .url(responseDTO.getUrl())
                .type(responseDTO.getType())
                .publicId(responseDTO.getPublicId())
                .place(place)
                .build();
    }

//    PHƯƠNG THỨC TẠO PLACE MỚI
    @Transactional
    @Override
    public PlaceResponseDTO createPlace(PlaceRequestDTO requestDTO, List<MultipartFile> files) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(()->new BadRequestException("Không tìm thấy danh mục với ID: "+requestDTO.getCategoryId()));
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(()->new BadRequestException("Không tìm thấy người dùng với ID: "+requestDTO.getUserId()));
        Place place = placeMapper.toEntity(requestDTO);

        place.setCategory(category);
        place.setUser(user);

        List<PlaceMedia> placeMediaList = new ArrayList<>();
        if(files!=null && !files.isEmpty()){
            for(MultipartFile file: files){
                CloudinaryUploadResponseDTO responseDTO = cloudinaryService.uploadFile(file);
                placeMediaList.add(toEntity(responseDTO,place));
            }
        }

        place.setPlaceMediaList(placeMediaList);

        if(!placeMediaList.isEmpty()){
            placeMediaList.get(0).setThumbnail(true);
            place.setCoverImageUrl(placeMediaList.get(0).getUrl());
        }

        Place savedPlace = placeRepository.save(place);

        return placeMapper.toResponseDTO(savedPlace);

    }

//    PHƯƠNG THỨC LẤY DETAIL CỦA PLACE THEO PLACEID
    @Override
    public PlaceResponseDTO getPlaceDetail(Long placeId) {
        Place place = placeRepository.findByIdAndStatus(placeId, PlaceStatus.APPROVED)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy địa điểm với ID: "+placeId));

        return placeMapper.toResponseDTO(place);
    }

//    PHƯƠNG THỨC LẤY TẤT CẢ PLACE THEO CONDITION
    @Override
    public Page<PlaceResponseDTO> getPlaceByConditions(String title, Double minRating, Pageable pageable) {
        Specification<Place> specification =
                Specification.where(PlaceSpecification.hasTitle(title))
                        .and(PlaceSpecification.hasMinRating(minRating))
                        .and(PlaceSpecification.hasStatus(PlaceStatus.APPROVED));

        Page<Place> places = placeRepository.findAll(specification,pageable);
        return places.map(placeMapper::toResponseDTO);
    }
}
