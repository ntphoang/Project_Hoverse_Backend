package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.placeFavorite.PlaceFavoriteResponseDTO;
import com.hoverse.backend.entity.PlaceFavorite;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@Mapper(componentModel = "spring")
public interface PlaceFavoriteMapper {
    PlaceFavoriteResponseDTO toResponseDTO(PlaceFavorite placeFavorite);
}
