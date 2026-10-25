package com.hoverse.backend.dto.place;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceSourceResponseDTO {

    private Long id;

    private String sourceUrl;

    private String creatorUsername;

    private LocalDateTime permissionGrantedAt;
}