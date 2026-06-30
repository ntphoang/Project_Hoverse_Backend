package com.hoverse.backend.repository;

import com.hoverse.backend.entity.Place;
import com.hoverse.backend.enums.PlaceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place,Long>, JpaSpecificationExecutor<Place> {
    Optional<Place> findByIdAndStatus(Long id, PlaceStatus status);
}
