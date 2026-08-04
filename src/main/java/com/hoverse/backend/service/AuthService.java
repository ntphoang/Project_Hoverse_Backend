package com.hoverse.backend.service;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
    void verifyEmail(String token);
    void resendVerify(String email);
}
