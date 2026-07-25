package com.hoverse.backend.dto;

import com.hoverse.backend.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 25/07/2026
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceMediaResponseDTO {
    private Long id;
    private String url;
    private MediaType type;
}
