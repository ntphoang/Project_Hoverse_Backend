package com.hoverse.backend.repository;

import com.hoverse.backend.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    Page<Review> findReviewsByPlaceIdAndDeletedAtIsNull(Long placeId, Pageable pageable);

    boolean existsByUserIdAndPlaceIdAndDeletedAtIsNull(Long userId, Long placeId);
}
