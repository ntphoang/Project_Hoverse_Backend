package com.hoverse.backend.dto;

import lombok.Data;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
