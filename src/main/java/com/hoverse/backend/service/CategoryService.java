package com.hoverse.backend.service;

import com.hoverse.backend.dto.category.CategoryCreateRequestDTO;
import com.hoverse.backend.dto.category.CategoryResponseDTO;
import com.hoverse.backend.dto.category.CategoryUpdateRequestDTO;
import com.hoverse.backend.entity.Category;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO createCategory(CategoryCreateRequestDTO requestDTO);
    CategoryResponseDTO changeCategoryStatus(Long categoryId);
    CategoryResponseDTO updateCategory(Long categoryId,CategoryUpdateRequestDTO requestDTO);
}
