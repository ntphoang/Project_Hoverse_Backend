package com.hoverse.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 16/06/2026
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal){
        String email = principal.getName();
        return ResponseEntity.ok("Chào mừng "+email+"! Trạm gác axios đã tự động trình thẻ JWT thành công.");
    }
}
