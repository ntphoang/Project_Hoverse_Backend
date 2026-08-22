package com.hoverse.backend.dto.review;

import com.hoverse.backend.enums.ReviewStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 22/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewChangeStatusRequestDTO {
    @NotNull(message = "Vui lòng chọn trạng thái cho bài đánh giá!")
    private ReviewStatus status;

    @Size(max = 100, message = "Lý do tối da 500 ký tự!")
    private String  reason;
}
