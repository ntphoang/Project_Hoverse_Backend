package com.hoverse.backend.service;

import com.hoverse.backend.dto.ReviewRequestDTO;
import com.hoverse.backend.dto.ReviewResponseDTO;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
public interface ReviewService {
    ReviewResponseDTO createReview(Long placeId,String email,ReviewRequestDTO reviewRequestDTO);
}
