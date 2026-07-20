package com.hoverse.backend.dto;

import com.hoverse.backend.entity.PlaceMedia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
@Getter
@Setter
public class PlaceRequestDTO {
    @NotBlank(message = "Tên địa điểm không được để trống")
    private String title;

    @NotBlank(message = "Tên địa chỉ không được để trống")
    private String address;

    @NotNull(message = "Vĩ độ không được để trống")
    private BigDecimal latitude;

    @NotNull(message = "Kinh độ không được để trống")
    private BigDecimal longitude;

    @NotNull(message = "Danh mục không được để trống (category_id)")
    private Long categoryId;

    @NotNull(message = "Người tạo không được để trống (user_id)")
    private Long userId;

    private String description;

    private Set<Long> tagIds;
}
