package com.hoverse.backend.repository;

import com.hoverse.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
