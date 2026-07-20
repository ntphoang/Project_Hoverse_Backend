package com.hoverse.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String coverImageUrl;

    private BigDecimal avgRating;
    private Integer reviewCount;

    private String categoryName;
    private String categorySlug;
    private String authorName;

    private Set<TagResponseDTO> tags;
}
