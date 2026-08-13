package com.hoverse.backend.repository;

import com.hoverse.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/08/2026
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findRefreshTokenByToken(String token);
}
