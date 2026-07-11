package com.hoverse.backend.service;

import com.hoverse.backend.dto.PlaceFilterRequestDTO;
import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.mapper.PlaceMapper;
import com.hoverse.backend.repository.PlaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
public interface PlaceService {
    PlaceResponseDTO createPlace(PlaceRequestDTO requestDTO, List<MultipartFile> files);
    PlaceResponseDTO getPlaceDetail(Long placeId);
    Page<PlaceResponseDTO> getPlaceByConditions(PlaceFilterRequestDTO filterRequestDTO,Pageable pageable);
}
