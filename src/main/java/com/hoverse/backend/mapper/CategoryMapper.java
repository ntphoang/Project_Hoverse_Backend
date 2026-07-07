package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.CategoryResponseDTO;
import com.hoverse.backend.entity.Category;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDTO toResponseDTO(Category category);
}
