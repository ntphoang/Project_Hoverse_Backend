package com.hoverse.backend.mapper;

import com.hoverse.backend.dto.ReverseGeocodeResponseDTO;
import com.hoverse.backend.dto.nominatim.NominatimResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
@Mapper(componentModel = "spring")
public interface NominatimMapper {
    @Mapping(source = "address.houseNumber",target = "houseNumber")
    @Mapping(source = "address.road",target = "road")
    @Mapping(source = "address.city", target = "city")
    ReverseGeocodeResponseDTO toReverse(NominatimResponseDTO nominatimResponseDTO);
}
