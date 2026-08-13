package com.hoverse.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResultDTO {
    private AuthResponseDTO responseDTO;
    private String refreshToken;
}
