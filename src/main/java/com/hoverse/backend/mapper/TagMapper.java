package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.tag.TagResponseDTO;
import com.hoverse.backend.entity.Tag;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Mapper(componentModel = "spring")
public interface TagMapper{
    TagResponseDTO toResponseDTO(Tag tag);
}
