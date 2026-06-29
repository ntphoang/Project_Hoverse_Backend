package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.ReviewResponseDTO;
import com.hoverse.backend.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/06/2026
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.username",target = "username")
    @Mapping(source = "user.avatarUrl",target = "avatarUrl")
    @Mapping(source = "place.title",target = "placeTitle")
    ReviewResponseDTO toResponseDTO(Review review);
}
