package com.hoverse.backend.repository;

import com.hoverse.backend.dto.tag.TagResponseDTO;
import com.hoverse.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    boolean existsTagByName(String name);
    boolean existsTagByNameAndIdIsNot(String name, Long id);
    List<Tag> findAllByIsActive(Boolean isActive);
}
