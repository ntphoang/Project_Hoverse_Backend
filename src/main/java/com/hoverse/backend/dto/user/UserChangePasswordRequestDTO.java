package com.hoverse.backend.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordRequestDTO {
    @NotBlank(message = "Mật khẩu cũ không được bỏ trống!")
    @Size(min = 6, max = 20, message = "Độ dài mật khẩu cũ phải từ 6 -> 20!")
    private String oldPassword;

    @NotBlank(message = "Mật khẩu mới không được bỏ trống!")
    @Size(min = 6, max = 20, message = "Độ dài mật khẩu mới phải từ 6 -> 20!")
    private String newPassword;
}
