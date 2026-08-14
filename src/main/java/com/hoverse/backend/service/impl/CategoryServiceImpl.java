package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.category.CategoryCreateRequestDTO;
import com.hoverse.backend.dto.category.CategoryResponseDTO;
import com.hoverse.backend.entity.Category;
import com.hoverse.backend.exception.DataIntegrityViolationException;
import com.hoverse.backend.mapper.CategoryMapper;
import com.hoverse.backend.repository.CategoryRepository;
import com.hoverse.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryCreateRequestDTO requestDTO) {
        boolean isExisted = categoryRepository.existsBySlug(requestDTO.getSlug());

        if(isExisted){
            throw new DataIntegrityViolationException("Slug của category đã tồn tại!");
        }

        Category categorySaved =  categoryRepository.save(categoryMapper.toEntity(requestDTO));
        return categoryMapper.toResponseDTO(categorySaved);
    }
}
