package com.hoverse.backend.dto.user;

import com.hoverse.backend.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 12/08/2026
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangeStatusRequestDTO {
    @NotNull(message = "Vui lòng chọn trạng thái của user!")
    private UserStatus status;
    private String reason;
}
