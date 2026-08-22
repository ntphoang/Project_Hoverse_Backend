package com.hoverse.backend.dto.review;

import com.hoverse.backend.enums.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewFilterRequestDTO {
    private Integer year;
    private Integer month;

    private ReviewStatus status;
    private Integer rating;
}
