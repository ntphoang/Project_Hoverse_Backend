package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/07/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudinaryDeleteResponseDTO {
    private int status;
    private String message;
}
