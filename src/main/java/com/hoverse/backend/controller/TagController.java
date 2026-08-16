package com.hoverse.backend.controller;

import com.hoverse.backend.dto.tag.TagRequestDTO;
import com.hoverse.backend.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getAllTags(@RequestParam(required = false) Boolean activeOnly){
        return ResponseEntity.ok(tagService.getAllTags(activeOnly));
    }

    @PatchMapping("/{tagId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeTagStatus(@PathVariable Long tagId){
        return ResponseEntity.ok(tagService.changeTagStatus(tagId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTag(@RequestBody @Valid TagRequestDTO requestDTO){
        return ResponseEntity.ok(tagService.createTag(requestDTO));
    }

    @PatchMapping("/{tagId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTag(@RequestBody @Valid TagRequestDTO requestDTO, @PathVariable Long tagId){
        return ResponseEntity.ok(tagService.updateTag(requestDTO, tagId));
    }
}
