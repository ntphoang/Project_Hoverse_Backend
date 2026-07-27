package com.hoverse.backend.dto.review;

import com.hoverse.backend.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 27/07/2026
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewMediaResponseDTO {
    private Long id;
    private String url;
    private MediaType type;
}
