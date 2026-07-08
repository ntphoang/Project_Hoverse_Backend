package com.hoverse.backend.controller;

import com.hoverse.backend.dto.ReverseGeocodeResponseDTO;
import com.hoverse.backend.service.NominatimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
@RestController
@RequestMapping("/api/v1/reverse")
@RequiredArgsConstructor
public class NominatimController {
    private final NominatimService nominatimService;

    @GetMapping
    public ResponseEntity<?> reverseGeocode(@RequestParam Double latitude, @RequestParam Double longitude){
        ReverseGeocodeResponseDTO response = nominatimService.reverseGeocode(latitude,longitude);
        return ResponseEntity.ok(response);
    }
}
