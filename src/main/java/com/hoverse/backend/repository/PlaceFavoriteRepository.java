package com.hoverse.backend.repository;

import com.hoverse.backend.entity.PlaceFavorite;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@Repository
public interface PlaceFavoriteRepository extends JpaRepository<PlaceFavorite, PlaceFavorite.PlaceFavoriteId> {
    @Query("select p.place.id from PlaceFavorite p where p.user.id = :userId")
    List<Long> getPlaceFavoriteIdByUserId(@Param("userId") Long userId);
}
