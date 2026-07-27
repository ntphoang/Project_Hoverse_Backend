package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.review.ReviewMediaResponseDTO;
import com.hoverse.backend.entity.ReviewMedia;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 27/07/2026
 */
@Mapper(componentModel = "spring")
public interface ReviewMediaMapper {
    ReviewMediaResponseDTO toResponseDTO(ReviewMedia reviewMedia);
}
