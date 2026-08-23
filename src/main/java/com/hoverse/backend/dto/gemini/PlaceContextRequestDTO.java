package com.hoverse.backend.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceContextRequestDTO {
    private Long id;
    private String title;
    private String address;
    private String description;
    private List<String> tags;
}
