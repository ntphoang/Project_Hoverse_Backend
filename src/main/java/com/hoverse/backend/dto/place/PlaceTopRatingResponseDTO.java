package com.hoverse.backend.dto.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 18/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceTopRatingResponseDTO {
    private Long id;
    private String title;
    private String address;
    private String coverImageUrl;
    private Integer reviewCount;
    private BigDecimal avgRating;
}
