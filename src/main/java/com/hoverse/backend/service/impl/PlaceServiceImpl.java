package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.PlaceRepository;
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
    private final PlaceMapper placeMapper;

    @Override
    public List<PlaceResponseDTO> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(placeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
