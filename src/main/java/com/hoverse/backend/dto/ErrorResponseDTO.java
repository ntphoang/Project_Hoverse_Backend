package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 22/06/2026
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime time;
    private String message;
    private String code;
}
