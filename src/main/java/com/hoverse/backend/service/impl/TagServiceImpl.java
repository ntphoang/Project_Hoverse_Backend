package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.tag.TagRequestDTO;
import com.hoverse.backend.dto.tag.TagResponseDTO;
import com.hoverse.backend.entity.Tag;
import com.hoverse.backend.exception.DataIntegrityViolationException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.TagMapper;
import com.hoverse.backend.repository.TagRepository;
import com.hoverse.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<TagResponseDTO> getAllTags(Boolean isActive) {
        if(isActive == null){
            return tagRepository.findAll()
                    .stream()
                    .map(tagMapper::toResponseDTO)
                    .toList();
        }

        return tagRepository.findAllByIsActive(isActive)
                .stream()
                .map(tagMapper::toResponseDTO)
                .toList();
    }

    @Override
    public TagResponseDTO changeTagStatus(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy tag với id: "+tagId));

        tag.setIsActive(!tag.getIsActive());
        Tag tagSaved = tagRepository.save(tag);

        return tagMapper.toResponseDTO(tagSaved);
    }

    @Override
    public TagResponseDTO createTag(TagRequestDTO requestDTO) {
        boolean isExisted = tagRepository.existsTagByName(requestDTO.getName());

        if(isExisted){
            throw new DataIntegrityViolationException("Tag với tên: "+requestDTO.getName()+" đã tồn tại!");
        }

        Tag tag = Tag.builder()
                .name(requestDTO.getName())
                .iconName(requestDTO.getIconName())
                .isActive(true)
                .build();

        Tag tagSaved = tagRepository.save(tag);
        return tagMapper.toResponseDTO(tagSaved);
    }

    @Override
    public TagResponseDTO updateTag(TagRequestDTO requestDTO, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy tag với id: "+tagId));

        boolean isExisted = tagRepository.existsTagByNameAndIdIsNot(requestDTO.getName(),tag.getId());
        if(isExisted){
            throw new DataIntegrityViolationException("Tag với name: "+requestDTO.getName()+" đã tồn tại!");
        }

        tag.setName(requestDTO.getName());
        tag.setIconName(requestDTO.getIconName());

        Tag tagSaved = tagRepository.save(tag);
        return tagMapper.toResponseDTO(tagSaved);
    }

}
