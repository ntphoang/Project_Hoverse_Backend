package com.hoverse.backend.service;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 03/08/2026
 */
public interface EmailService {
    void sendVerificationEmail(String toEmail, String token);
}
