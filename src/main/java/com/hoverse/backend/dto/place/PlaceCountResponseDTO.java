package com.hoverse.backend.dto.place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 20/08/2026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceCountResponseDTO {
    private int month;
    private long count;
}
