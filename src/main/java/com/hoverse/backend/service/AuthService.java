package com.hoverse.backend.service;

import com.hoverse.backend.dto.AuthRequest;
import com.hoverse.backend.dto.AuthResponse;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
