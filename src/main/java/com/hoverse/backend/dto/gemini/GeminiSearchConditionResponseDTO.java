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
public class GeminiSearchConditionResponseDTO {
    private String category;
    private String location;
    private List<String> tags;
}
