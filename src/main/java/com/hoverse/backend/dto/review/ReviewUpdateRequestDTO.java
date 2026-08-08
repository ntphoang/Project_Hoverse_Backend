package com.hoverse.backend.dto.review;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewUpdateRequestDTO {
    @NotNull(message = "Vui lòng chọn số sao cho địa diểm này!")
    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank(message = "Vui lòng viết thêm một chút đánh giá!")
    @Size(min = 5, max = 500)
    private String content;

    private List<Long> medias;
}
