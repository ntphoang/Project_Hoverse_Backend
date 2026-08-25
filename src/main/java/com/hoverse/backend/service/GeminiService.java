package com.hoverse.backend.service;

import com.hoverse.backend.dto.gemini.GeminiRecommendResponseDTO;
import com.hoverse.backend.dto.gemini.GeminiSearchConditionResponseDTO;
import com.hoverse.backend.dto.gemini.PlaceContextRequestDTO;

import java.util.List;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
public interface GeminiService {
    List<GeminiRecommendResponseDTO> recommendPlaces(String userRequirement);
    GeminiSearchConditionResponseDTO extractSearchConditions(String userRequirement);
    List<PlaceContextRequestDTO> processRecommendation(String userPrompt);
}
