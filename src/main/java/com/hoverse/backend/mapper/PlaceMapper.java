package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.place.PlaceChangeStatusResponseDTO;
import com.hoverse.backend.dto.place.PlaceRequestDTO;
import com.hoverse.backend.dto.place.PlaceResponseDTO;
import com.hoverse.backend.dto.place.PlaceTopRatingResponseDTO;
import com.hoverse.backend.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Mapper(componentModel = "spring",uses = {TagMapper.class, PlaceMediaMapper.class} )
public interface PlaceMapper {
    @Mapping(source = "category.id",target = "categoryId")
    @Mapping(source = "category.name",target = "categoryName")
    @Mapping(source = "category.slug",target = "categorySlug")
    @Mapping(source = "user.username",target = "authorName")
    @Mapping(source = "user.email",target = "authorEmail")
    @Mapping(source = "user.avatarUrl",target = "authorAvatarUrl")
    PlaceResponseDTO toResponseDTO(Place place);

    @Mapping(source = "categoryId",target = "category.id")
    Place toEntity(PlaceRequestDTO dto);

    PlaceChangeStatusResponseDTO toChangeStatusResponseDTO(Place place);

    PlaceTopRatingResponseDTO toTopRatingResponseDTO(Place place);
}
