package com.hoverse.backend.dto.place;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaceCreateByAdminRequestDTO{

    @NotNull
    @Valid
    private PlaceRequestDTO place;

    @NotBlank
    private String sourceUrl;

    @NotBlank
    private String creatorUsername;

    @NotNull
    private LocalDateTime permissionGrantedAt;

    @NotNull
    private Boolean permissionConfirmed;
}