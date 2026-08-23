package com.hoverse.backend.controller;

import com.hoverse.backend.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 23/08/2026
 */
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class GeminiController {
    private final GeminiService geminiService;

    @GetMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestParam String prompt){
        return ResponseEntity.ok(geminiService.processRecommendation(prompt));
    }
}
