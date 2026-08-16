package com.hoverse.backend.service;

import com.hoverse.backend.dto.tag.TagRequestDTO;
import com.hoverse.backend.dto.tag.TagResponseDTO;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
public interface TagService {
    List<TagResponseDTO> getAllTags(Boolean activeOnly);
    TagResponseDTO changeTagStatus(Long tagId);
    TagResponseDTO createTag(TagRequestDTO requestDTO);
    TagResponseDTO updateTag(TagRequestDTO requestDTO, Long tagId);
}
