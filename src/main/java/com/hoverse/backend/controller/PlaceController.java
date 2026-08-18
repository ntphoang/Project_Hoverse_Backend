package com.hoverse.backend.controller;

import com.hoverse.backend.dto.place.*;
import com.hoverse.backend.dto.placeFavorite.PlaceFavoriteResponseDTO;
import com.hoverse.backend.service.PlaceFavoriteService;
import com.hoverse.backend.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
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
    private final PlaceFavoriteService placeFavoriteService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlaceResponseDTO> createPlace(
            Principal principal,
            @RequestPart(value = "place") @Valid PlaceRequestDTO requestDTO,
            @RequestPart(value = "files",required = false) List<MultipartFile> files
    ){
        String email = principal.getName();
        PlaceResponseDTO createPlace = placeService.createPlace(email,requestDTO,files);
        return ResponseEntity.ok(createPlace);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> getPlaceDetail(@PathVariable Long id){
        PlaceResponseDTO place = placeService.getPlaceDetail(id);
        return ResponseEntity.ok(place);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceResponseDTO> updatePlace(
            @PathVariable Long id,
            Principal principal,
            @RequestPart(value = "place") PlaceUpdateRequestDTO requestDTO,
            @RequestPart(value = "files",required = false) List<MultipartFile> files

    ){
        String email = principal.getName();
        return ResponseEntity.ok(placeService.updatePlace(id,email,requestDTO,files));
    }

    @GetMapping
    public ResponseEntity<?> getPlaceByConditions(@ModelAttribute PlaceFilterRequestDTO filterRequestDTO, Pageable pageable){
        Page<PlaceResponseDTO> places = placeService.getPlaceByConditions(filterRequestDTO,pageable);
        return ResponseEntity.ok(places);
    }

    @PostMapping("/{placeId}/favorite")
    public ResponseEntity<?> toggleFavorite(@PathVariable Long placeId,Principal principal){
        PlaceFavoriteResponseDTO responseDTO = placeFavoriteService.toggleFavorite(principal.getName(),placeId);
        if(responseDTO == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{placeId}/view")
    public ResponseEntity<?> updateViewCount(@PathVariable Long placeId){
        return ResponseEntity.ok(placeService.updateViewCount(placeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{placeId}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long placeId,@RequestBody @Valid PlaceChangeStatusRequestDTO requestDTO){
        return ResponseEntity.ok(placeService.changeStatus(placeId ,requestDTO));
    }

    @GetMapping("/top-rating")
    public ResponseEntity<?> getPlacesTopRating(@RequestParam(defaultValue = "1") Integer reviewCount){
        return ResponseEntity.ok(placeService.getPlacesTopRating(reviewCount));
    }
}
