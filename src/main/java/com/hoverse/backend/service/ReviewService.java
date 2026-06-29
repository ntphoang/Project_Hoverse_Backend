package com.hoverse.backend.service;

import com.hoverse.backend.dto.ReviewRequestDTO;
import com.hoverse.backend.dto.ReviewResponseDTO;
import com.hoverse.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
public interface ReviewService {
    ReviewResponseDTO createReview(Long placeId,String email,ReviewRequestDTO reviewRequestDTO);
    Page<ReviewResponseDTO> findReviewsByPlaceId(Long placeId, Pageable pageable);
}
