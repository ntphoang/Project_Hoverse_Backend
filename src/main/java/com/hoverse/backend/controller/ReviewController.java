package com.hoverse.backend.controller;

import com.hoverse.backend.dto.review.*;
import com.hoverse.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

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

    @PostMapping(value = "/{id}/reviews",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createReview(
            @RequestPart(value = "review") @Valid ReviewRequestDTO requestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @PathVariable Long id,
            Principal principal
            ){
        String email = principal.getName();
        ReviewResponseDTO responseDTO = reviewService.createReview(id,email,requestDTO,files);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewsOfPlace(@PathVariable Long id, Pageable pageable){
        Page<ReviewResponseDTO> reviewResponseDTOS = reviewService.findReviewsByPlaceId(id,pageable);
        return ResponseEntity.ok(reviewResponseDTOS);
    }

    @PatchMapping(value = "/reviews/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateReview(
            Principal principal,
            @PathVariable Long reviewId,
            @RequestPart(value = "review") @Valid ReviewUpdateRequestDTO requestDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files){
        String email = principal.getName();
        return ResponseEntity.ok(reviewService.updateReview(email,reviewId,requestDTO,files));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(Principal principal,@PathVariable Long reviewId){
        String email = principal.getName();
        return ResponseEntity.ok(reviewService.deleteReview(email,reviewId));
    }

    @GetMapping("/admin/reviews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getReviewsByConditions(@ModelAttribute ReviewFilterRequestDTO requestDTO, Pageable pageable){
        return ResponseEntity.ok(reviewService.getReviewsByConditions(requestDTO, pageable));
    }

    @PatchMapping("/admin/reviews/{reviewId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeReviewStatus(@PathVariable Long reviewId, @RequestBody @Valid ReviewChangeStatusRequestDTO requestDTO){
        return ResponseEntity.ok(reviewService.changeReviewStatus(reviewId, requestDTO));
    }
}
