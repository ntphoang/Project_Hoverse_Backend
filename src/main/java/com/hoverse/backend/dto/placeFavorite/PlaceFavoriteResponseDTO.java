package com.hoverse.backend.dto.placeFavorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceFavoriteResponseDTO {
    private Long userId;
    private Long placeId;
    private LocalDateTime createdAt;
}
