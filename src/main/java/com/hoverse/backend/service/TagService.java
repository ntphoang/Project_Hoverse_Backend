package com.hoverse.backend.service;

import com.hoverse.backend.dto.TagResponseDTO;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
public interface TagService {
    List<TagResponseDTO> getAllTags();
}
