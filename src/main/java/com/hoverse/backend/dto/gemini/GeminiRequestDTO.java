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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeminiRequestDTO {
    private List<GeminiContent> contents;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GeminiContent{
        private List<GeminiPart> parts;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GeminiPart{
        private String text;
    }
}
