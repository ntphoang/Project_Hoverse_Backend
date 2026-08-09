package com.hoverse.backend.service;

import com.hoverse.backend.dto.place.PlaceFilterRequestDTO;
import com.hoverse.backend.dto.place.PlaceRequestDTO;
import com.hoverse.backend.dto.place.PlaceResponseDTO;
import com.hoverse.backend.dto.place.PlaceUpdateRequestDTO;
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
    PlaceResponseDTO createPlace(String email,PlaceRequestDTO requestDTO, List<MultipartFile> files);
    PlaceResponseDTO getPlaceDetail(Long placeId);
    Page<PlaceResponseDTO> getPlaceByConditions(PlaceFilterRequestDTO filterRequestDTO,Pageable pageable);
    PlaceResponseDTO updatePlace(Long placeId, String email, PlaceUpdateRequestDTO requestDTO, List<MultipartFile> files);
    int updateViewCount(Long placeId);
}
