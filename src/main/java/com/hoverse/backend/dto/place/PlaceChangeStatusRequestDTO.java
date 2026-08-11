package com.hoverse.backend.dto.place;

import com.hoverse.backend.enums.PlaceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 10/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceChangeStatusRequestDTO {
    @NotNull(message = "Vui lòng chọn trạng thái cho địa điểm")
    private PlaceStatus status;
    private String rejectReason;
}
