package com.hoverse.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
@Data
@Builder
@AllArgsConstructor
public class ReverseGeocodeResponseDTO {
    private String displayName;
    private String houseNumber;
    private String road;
    private String city;
}
