package com.hoverse.backend.service;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.PlaceRepository;

import java.util.List;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
public interface PlaceService {
    List<PlaceResponseDTO> getAllPlaces();
    PlaceResponseDTO createPlace(PlaceRequestDTO requestDTO);
}
