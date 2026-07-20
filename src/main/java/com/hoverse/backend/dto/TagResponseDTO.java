package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/07/2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDTO {
    private Long id;
    private String name;
    private String iconName;
    private boolean isActive;
}
