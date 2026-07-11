package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 11/07/2026
 */
@Data
@Builder
public class PlaceFilterRequestDTO {
    private String title;
    private Long categoryId;
    private Double minRating;
}
