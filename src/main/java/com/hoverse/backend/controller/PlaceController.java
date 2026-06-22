package com.hoverse.backend.controller;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<PlaceResponseDTO> createPlace(@Valid @RequestBody PlaceRequestDTO requestDTO){
        PlaceResponseDTO createPlace = placeService.createPlace(requestDTO);
        return ResponseEntity.ok(createPlace);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> getPlaceDetail(@PathVariable Long id){
        PlaceResponseDTO place = placeService.getPlaceDetail(id);
        return ResponseEntity.ok(place);
    }
}
