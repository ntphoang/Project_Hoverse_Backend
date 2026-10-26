package com.hoverse.backend.dto.tag;

import lombok.*;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 26/10/2026
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagFilterRequestDTO {
    private Boolean isActive;
    private Boolean hasPlace;
}
