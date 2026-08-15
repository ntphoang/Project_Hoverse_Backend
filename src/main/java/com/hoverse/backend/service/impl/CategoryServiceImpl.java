package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.category.CategoryCreateRequestDTO;
import com.hoverse.backend.dto.category.CategoryFilterRequestDTO;
import com.hoverse.backend.dto.category.CategoryResponseDTO;
import com.hoverse.backend.dto.category.CategoryUpdateRequestDTO;
import com.hoverse.backend.entity.Category;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.DataIntegrityViolationException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.CategoryMapper;
import com.hoverse.backend.repository.CategoryRepository;
import com.hoverse.backend.repository.specification.CategorySpecification;
import com.hoverse.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
    public List<CategoryResponseDTO> getCategoryByConditions(CategoryFilterRequestDTO requestDTO) {
        Specification<Category> specification =
                Specification.where(CategorySpecification.isActive(requestDTO.getIsActive()));

        List<Category> categories = categoryRepository.findAll(specification);
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

    @Override
    public CategoryResponseDTO changeCategoryStatus(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy category với id: "+categoryId));

        category.setIsActive(!category.getIsActive());
        Category categorySaved = categoryRepository.save(category);

        return categoryMapper.toResponseDTO(categorySaved);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long categoryId,CategoryUpdateRequestDTO requestDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm tháy category với id: "+categoryId));

        if(categoryRepository.existsBySlugAndIdNot(requestDTO.getSlug(), categoryId)){
            throw new DataIntegrityViolationException("slug đã tồn tại. Vui lòng chọn slug khác!");
        }

        category.setSlug(requestDTO.getSlug());
        category.setName(requestDTO.getName());
        category.setIconName(requestDTO.getIconName());

        Category categorySaved = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(categorySaved);
    }


}
