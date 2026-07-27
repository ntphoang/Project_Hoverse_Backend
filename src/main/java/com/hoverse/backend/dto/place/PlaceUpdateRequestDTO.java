package com.hoverse.backend.dto.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 24/07/2026
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceUpdateRequestDTO {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @Size(message = "Mô tả tối đa 200 ký tự",max = 200)
    private String description;

    @NotNull(message = "Địa chỉ không được bỏ trống")
    private String address;

    @NotNull(message = "Tọa độ không được bỏ trống")
    private BigDecimal latitude;

    @NotNull(message = "Tọa độ không được bỏ trống")
    private BigDecimal longitude;

    @NotNull(message = "Danh mục không được bỏ trống")
    private Long categoryId;

    private List<Long> placeMediaIds;
    private List<Long> tagIds;
}
