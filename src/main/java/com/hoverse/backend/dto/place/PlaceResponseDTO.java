package com.hoverse.backend.dto.place;

import com.hoverse.backend.dto.tag.TagResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime createdAt;

    private BigDecimal avgRating;
    private Integer reviewCount;

    private Long categoryId;
    private String categoryName;
    private String categorySlug;

    private String authorName;
    private String authorEmail;

    private Set<TagResponseDTO> tags;

    private List<PlaceMediaResponseDTO> placeMediaList;
}
