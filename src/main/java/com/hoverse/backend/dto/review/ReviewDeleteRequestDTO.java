package com.hoverse.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDeleteRequestDTO {
    private String rejectReason;
}
