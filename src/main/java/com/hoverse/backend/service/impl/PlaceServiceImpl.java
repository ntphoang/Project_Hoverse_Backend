package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.entity.Category;
import com.hoverse.backend.entity.Place;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.PlaceStatus;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.CategoryRepository;
import com.hoverse.backend.repository.PlaceRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PlaceResponseDTO> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .filter(place -> place.getStatus()==PlaceStatus.APPROVED)
                .map(placeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceResponseDTO createPlace(PlaceRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(()->new RuntimeException("Không tìm thấy danh mục với ID: "+requestDTO.getCategoryId()));
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(()->new RuntimeException("Không tìm thấy người dùng với ID: "+requestDTO.getUserId()));
        Place place = placeMapper.toEntity(requestDTO);

        place.setCategory(category);
        place.setUser(user);

        Place savedPlace = placeRepository.save(place);

        return placeMapper.toResponseDTO(savedPlace);

    }

    @Override
    public PlaceResponseDTO getPlaceDetail(Long placeId) {
        Place place = placeRepository.findByIdAndStatus(placeId, PlaceStatus.APPROVED)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy địa điểm với ID: "+placeId));

        return placeMapper.toResponseDTO(place);
    }
}
