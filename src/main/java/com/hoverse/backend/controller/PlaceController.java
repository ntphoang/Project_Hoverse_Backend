package com.hoverse.backend.controller;

import com.hoverse.backend.dto.place.PlaceFilterRequestDTO;
import com.hoverse.backend.dto.place.PlaceRequestDTO;
import com.hoverse.backend.dto.place.PlaceResponseDTO;
import com.hoverse.backend.dto.place.PlaceUpdateRequestDTO;
import com.hoverse.backend.service.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlaceResponseDTO> createPlace(
            @RequestPart(value = "place") @Valid PlaceRequestDTO requestDTO,
            @RequestPart(value = "files",required = false) List<MultipartFile> files
    ){
        PlaceResponseDTO createPlace = placeService.createPlace(requestDTO,files);
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
}
