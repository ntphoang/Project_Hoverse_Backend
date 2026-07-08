package com.hoverse.backend.client;

import com.hoverse.backend.dto.nominatim.NominatimResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 07/07/2026
 */
@Component
@RequiredArgsConstructor
public class NominatimClient {
    private final WebClient nominatimWebClient;

    public NominatimResponseDTO reverseGeocode(Double latitude, Double longitude){
        return nominatimWebClient
                .get()
                .uri(uriBuilder ->
                    uriBuilder
                            .path("/reverse")
                            .queryParam("lat",latitude)
                            .queryParam("lon",longitude)
                            .queryParam("format","jsonv2")
                            .build()
                )
                .retrieve()
                .bodyToMono(NominatimResponseDTO.class)
                .block();
    }
}
