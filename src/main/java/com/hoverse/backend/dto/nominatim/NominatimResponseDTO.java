package com.hoverse.backend.dto.nominatim;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@Data
@Builder
@AllArgsConstructor
public class NominatimResponseDTO {
    @JsonProperty("display_name")
    private String displayName;
    private NominatimAddressDTO address;
}
