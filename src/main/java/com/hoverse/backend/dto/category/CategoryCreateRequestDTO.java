package com.hoverse.backend.dto.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 14/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateRequestDTO {
    @NotNull(message = "Vui lòng thêm tên cho category")
    @Max(30)
    private String name;

    @NotNull(message = "Vui lòng thêm slug cho category")
    @Max(30)
    private String slug;
    private String iconName;
}
