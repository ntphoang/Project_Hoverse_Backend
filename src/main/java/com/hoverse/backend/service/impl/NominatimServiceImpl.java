package com.hoverse.backend.service.impl;

import com.hoverse.backend.client.NominatimClient;
import com.hoverse.backend.dto.ReverseGeocodeResponseDTO;
import com.hoverse.backend.dto.nominatim.NominatimResponseDTO;
import com.hoverse.backend.mapper.NominatimMapper;
import com.hoverse.backend.service.NominatimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
@Service
@RequiredArgsConstructor
public class NominatimServiceImpl implements NominatimService {
    private final NominatimClient nominatimClient;
    private final NominatimMapper nominatimMapper;

    @Override
    public ReverseGeocodeResponseDTO reverseGeocode(Double latitude, Double longitude) {
        NominatimResponseDTO response = nominatimClient.reverseGeocode(latitude,longitude);

        if(response == null){
            throw new RuntimeException("Không tìm thấy vị trí với tọa độ:"+latitude+":"+longitude);
        }

        return nominatimMapper.toReverse(response);
    }
}
