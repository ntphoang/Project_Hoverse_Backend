package com.hoverse.backend.service;

import com.hoverse.backend.dto.AuthRequestDTO;
import com.hoverse.backend.dto.AuthResponseDTO;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
}
