package com.hoverse.backend.controller;

import com.hoverse.backend.dto.TagResponseDTO;
import com.hoverse.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 20/07/2026
 */
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> getAllTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
