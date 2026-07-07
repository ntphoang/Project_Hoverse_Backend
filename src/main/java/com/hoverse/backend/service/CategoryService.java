package com.hoverse.backend.service;

import com.hoverse.backend.dto.CategoryResponseDTO;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
}
