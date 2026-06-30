package com.hoverse.backend.controller;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<?> getPlaceByConditions(@RequestParam(required = false) String title, @RequestParam(required = false) Double minRating, Pageable pageable){
        Page<PlaceResponseDTO> places = placeService.getPlaceByConditions(title, minRating, pageable);
        return ResponseEntity.ok(places);
    }
}
