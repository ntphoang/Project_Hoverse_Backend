package com.hoverse.backend.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 14/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryFilterRequestDTO {
    private Boolean isActive;
}
