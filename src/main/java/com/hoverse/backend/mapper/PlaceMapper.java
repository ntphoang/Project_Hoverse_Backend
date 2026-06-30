package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Mapper(componentModel = "spring")
public interface PlaceMapper {
    @Mapping(source = "category.name",target = "categoryName")
    @Mapping(source = "category.slug",target = "categorySlug")
    @Mapping(source = "user.username",target = "authorName")
    PlaceResponseDTO toResponseDTO(Place place);

    @Mapping(source = "categoryId",target = "category.id")
    @Mapping(source = "userId",target = "user.id")
    Place toEntity(PlaceRequestDTO dto);
}
