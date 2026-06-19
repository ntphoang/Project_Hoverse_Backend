package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String email;
    private String role;
}
