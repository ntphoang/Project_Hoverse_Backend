package com.hoverse.backend.service;

import com.hoverse.backend.dto.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/07/2026
 */
public interface UserService {
    UserProfileResponseDTO getUserProfile(String email);
    UserProfileResponseDTO updateUserProfile(String email, UserUpdateProfileRequestDTO requestDTO);
    UserProfileResponseDTO uploadAvatar(String email, MultipartFile file);
    UserProfileResponseDTO changePassword(String email, UserChangePasswordRequestDTO requestDTO);
    UserProfileResponseDTO changeUserStatus(String email, Long userId, UserChangeStatusRequestDTO requestDTO);
    Page<UserProfileResponseDTO> getUserByConditions(UserFilterRequestDTO requestDTO, Pageable pageable);
}
