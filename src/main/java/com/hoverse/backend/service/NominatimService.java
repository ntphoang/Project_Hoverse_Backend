package com.hoverse.backend.service;

import com.hoverse.backend.dto.ReverseGeocodeResponseDTO;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
public interface NominatimService {
    ReverseGeocodeResponseDTO reverseGeocode(Double latitude, Double longitude);
}
