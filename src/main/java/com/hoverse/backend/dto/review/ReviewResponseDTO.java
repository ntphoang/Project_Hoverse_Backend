package com.hoverse.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/06/2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private Integer rating;
    private String content;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String rejectReason;

    private Long authorId;
    private String authorFullname;
    private String username;
    private String avatarUrl;

    private String placeTitle;

    private List<ReviewMediaResponseDTO> reviewMediaList;
}
