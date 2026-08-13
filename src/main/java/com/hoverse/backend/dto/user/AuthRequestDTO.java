package com.hoverse.backend.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
@Data
public class AuthRequestDTO {
    @NotNull(message = "Vui lòng nhập email!")
    private String email;

    @NotNull(message = "Vui lòng nhập mật khẩu")
    private String password;
}
