package com.hoverse.backend.dto.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/10/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceForMapResponseDTO {
    private Long id;
    private String title;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String coverImageUrl;
    private BigDecimal avgRating;
    private Integer reviewCount;
}
