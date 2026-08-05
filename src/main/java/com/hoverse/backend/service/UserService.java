package com.hoverse.backend.service;

import com.hoverse.backend.dto.user.UserProfileResponseDTO;
import com.hoverse.backend.dto.user.UserUpdateProfileRequestDTO;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/07/2026
 */
public interface UserService {
    UserProfileResponseDTO getUserProfile(String email);
    UserProfileResponseDTO updateUserProfile(String email, UserUpdateProfileRequestDTO requestDTO);
}
