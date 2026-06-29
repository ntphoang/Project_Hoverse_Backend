package com.hoverse.backend.repository;

import com.hoverse.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findReviewByUserIdAndPlaceId(Long userId, Long placeId);
    Page<Review> findReviewsByPlaceId (Long placeId, Pageable pageable);
}
