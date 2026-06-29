package com.hoverse.backend.controller;

import com.hoverse.backend.dto.ReviewRequestDTO;
import com.hoverse.backend.dto.ReviewResponseDTO;
import com.hoverse.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewRequestDTO requestDTO, @PathVariable Long id, Principal principal){
        String email = principal.getName();
        reviewService.createReview(id,email,requestDTO);
        return ResponseEntity.ok("Đã thêm đánh giá thành công!");
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewsOfPlace(@PathVariable Long id, Pageable pageable){
        Page<ReviewResponseDTO> reviewResponseDTOS = reviewService.findReviewsByPlaceId(id,pageable);
        return ResponseEntity.ok(reviewResponseDTOS);
    }
}
