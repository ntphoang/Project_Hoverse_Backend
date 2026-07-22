package com.hoverse.backend.controller;

import com.hoverse.backend.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(Principal principal){
        String email = principal.getName();
        return ResponseEntity.ok(userService.getUserProfile(email));
    }
}
