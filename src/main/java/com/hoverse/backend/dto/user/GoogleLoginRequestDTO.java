package com.hoverse.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/09/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleLoginRequestDTO {
    @NotBlank
    private String credential;
}
