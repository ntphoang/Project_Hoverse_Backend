package com.hoverse.backend.controller;

import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 31/05/2026
 */
@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> getAllPlaces(){
        List<PlaceResponseDTO> places = placeService.getAllPlaces();
        return ResponseEntity.ok(places);
    }
}
