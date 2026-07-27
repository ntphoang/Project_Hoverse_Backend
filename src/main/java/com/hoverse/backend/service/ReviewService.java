package com.hoverse.backend.service;

import com.hoverse.backend.dto.review.ReviewRequestDTO;
import com.hoverse.backend.dto.review.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
public interface ReviewService {
    ReviewResponseDTO createReview(Long placeId, String email, ReviewRequestDTO reviewRequestDTO, List<MultipartFile> files);
    Page<ReviewResponseDTO> findReviewsByPlaceId(Long placeId, Pageable pageable);
}
