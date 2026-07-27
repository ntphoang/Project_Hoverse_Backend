package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.place.PlaceMediaResponseDTO;
import com.hoverse.backend.entity.PlaceMedia;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 25/07/2026
 */
@Mapper(componentModel = "spring")
public interface PlaceMediaMapper {
    PlaceMediaResponseDTO toResponseDTO(PlaceMedia placeMedia);
}
