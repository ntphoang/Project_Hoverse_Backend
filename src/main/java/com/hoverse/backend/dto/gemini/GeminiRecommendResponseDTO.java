package com.hoverse.backend.dto.gemini;

import com.hoverse.backend.dto.place.PlaceResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/08/2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeminiRecommendResponseDTO {
    private PlaceContextRequestDTO placeContextRequestDTO;
    private String reason;
}
