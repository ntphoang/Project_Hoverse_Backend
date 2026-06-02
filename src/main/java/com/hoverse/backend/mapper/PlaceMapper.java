package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.PlaceRequestDTO;
import com.hoverse.backend.dto.PlaceResponseDTO;
import com.hoverse.backend.entity.Place;
import org.springframework.stereotype.Component;

/**
 * Project_TimKiemDiaDiemVuiChoi
 * Author: Phi Hoàng
 * Date: 29/05/2026
 */
@Component
public class PlaceMapper {
    public PlaceResponseDTO toResponseDTO(Place place){
        if(place == null){
            return null;
        }

        return PlaceResponseDTO.builder()
                .id(place.getId())
                .title(place.getTitle())
                .description(place.getDescription())
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .coverImageUrl(place.getCoverImageUrl())
                .avgRating(place.getAvgRating())
                .reviewCount(place.getReviewCount())
                .categoryName(place.getCategory() != null ? place.getCategory().getName() : null)
                .categorySlug(place.getCategory() != null ? place.getCategory().getSlug() : null)
                .authorName(place.getUser() != null ? place.getUser().getUsername() : "Anonymous")
                .build();
    }

    public Place toEntity(PlaceRequestDTO dto){
        if(dto == null){
            return null;
        }
        return Place.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .normalizedAddress(dto.getAddress().replace("[^a-zA-Z0-9]","").toLowerCase())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .coverImageUrl(dto.getCoverImageUrl())
                .build();
    }
}
