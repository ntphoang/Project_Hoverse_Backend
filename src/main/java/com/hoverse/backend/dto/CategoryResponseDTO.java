package com.hoverse.backend.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@Data
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String iconName;
}
