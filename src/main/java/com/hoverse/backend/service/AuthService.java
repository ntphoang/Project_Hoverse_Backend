package com.hoverse.backend.service;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;
import com.hoverse.backend.dto.user.AuthResultDTO;
import jakarta.servlet.http.Cookie;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResultDTO login(AuthRequestDTO request);
    void verifyEmail(String token);
    void resendVerify(String email);
    AuthResponseDTO refreshToken(String refreshTokenString);
}
