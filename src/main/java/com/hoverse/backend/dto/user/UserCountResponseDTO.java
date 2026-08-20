package com.hoverse.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 19/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCountResponseDTO {
    private int month;
    private long count;
}
