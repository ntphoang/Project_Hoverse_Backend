package com.hoverse.backend.controller;

import com.hoverse.backend.service.PlaceFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class PlaceFavoriteController {
    private final PlaceFavoriteService placeFavoriteService;

    @GetMapping("/ids")
    public ResponseEntity<?> getPlaceFavoriteId(Principal principal){
        String email = principal!=null ? principal.getName() : null;
        return ResponseEntity.ok(placeFavoriteService.getPlaceFavoriteId(email));
    }

    @GetMapping
    public ResponseEntity<?> getPlaceFavorites(Principal principal, Pageable pageable){
        String email = principal!=null ? principal.getName() : null;
        return ResponseEntity.ok(placeFavoriteService.getPlaceFavorites(email,pageable));
    }
}
