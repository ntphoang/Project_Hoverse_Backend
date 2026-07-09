package com.hoverse.backend.repository;

import com.hoverse.backend.entity.PlaceMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 09/07/2026
 */
@Repository
public interface PlaceMediaRepository extends JpaRepository<PlaceMedia,Long> {
}
