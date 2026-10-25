package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.place.PlaceSourceResponseDTO;
import com.hoverse.backend.entity.PlaceSource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceSourceMapper {
    PlaceSourceResponseDTO toResponseDTO(PlaceSource placeSource);
}