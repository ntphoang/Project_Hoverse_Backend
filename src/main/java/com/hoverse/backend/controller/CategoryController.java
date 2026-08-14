package com.hoverse.backend.controller;

import com.hoverse.backend.dto.category.CategoryCreateRequestDTO;
import com.hoverse.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController{
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCategory (@RequestBody @Valid CategoryCreateRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{categoryId}/status")
    public ResponseEntity<?> changeCategoryStatus (@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.changeCategoryStatus(categoryId));
    }
}
