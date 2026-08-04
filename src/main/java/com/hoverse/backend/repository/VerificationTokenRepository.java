package com.hoverse.backend.repository;

import com.hoverse.backend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 03/08/2026
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}
