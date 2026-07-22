package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.UserProfileResponseDTO;
import com.hoverse.backend.entity.User;
import org.mapstruct.Mapper;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/07/2026
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserProfileResponseDTO toResponseDTO(User user);
}
