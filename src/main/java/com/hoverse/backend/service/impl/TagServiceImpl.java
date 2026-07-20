package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.TagResponseDTO;
import com.hoverse.backend.entity.Tag;
import com.hoverse.backend.mapper.TagMapper;
import com.hoverse.backend.repository.TagRepository;
import com.hoverse.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagResponseDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toResponseDTO)
                .toList();
    }
}
