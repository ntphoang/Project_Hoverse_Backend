package com.hoverse.backend.dto.place;

import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private List<Long> tags;
}
