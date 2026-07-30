package com.hoverse.backend.service;

import com.hoverse.backend.dto.placeFavorite.PlaceFavoriteResponseDTO;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 29/07/2026
 */
public interface PlaceFavoriteService {
    PlaceFavoriteResponseDTO toggleFavorite(String email, Long placeId);
    List<Long> getPlaceFavoriteId(String email);
}
