package com.hoverse.backend.repository;

import com.hoverse.backend.entity.Place;
import com.hoverse.backend.enums.PlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place,Long>, JpaSpecificationExecutor<Place> {
    Optional<Place> findByIdAndStatus(Long id, PlaceStatus status);

    @Modifying
    @Query("update Place p set p.viewCount = p.viewCount + 1 where p.id = :placeId")
    int updateViewCount(@Param(value = "placeId") Long placeId);

    List<Place> findTop5ByReviewCountGreaterThanEqualAndStatusOrderByAvgRatingDescReviewCountDesc(Integer reviewCountIsGreaterThan, PlaceStatus status);
}
