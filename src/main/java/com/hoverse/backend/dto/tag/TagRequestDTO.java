package com.hoverse.backend.dto.tag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 16/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagRequestDTO {
    @NotBlank(message = "Vui lòng thêm tên cho tag!")
    @Size(min = 1, max = 30, message = "Tên tag phải dưới 30 ký tự!")
    private String name;

    @NotBlank(message = "Vui lòng thêm tên cho icon!")
    @Size(min = 1, max = 30, message = "Tên icon phải dưới 30 ký tự!")
    private String iconName;
}
