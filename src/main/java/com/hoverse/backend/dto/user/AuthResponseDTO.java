package com.hoverse.backend.dto.user;

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
    private Long id;
    private String token;
    private String email;
    private String role;
    private String fullName;
    private String avatarUrl;
    private boolean isEmailVerified;
}
