package com.hoverse.backend.controller;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.service.AuthService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request){
        try {
            AuthResponseDTO response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request){
        try {
            AuthResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tài khoản hoặc mật khẩu không chính xác!");
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String token){
        try {
            authService.verifyEmail(token);
            return ResponseEntity.ok("Xác thực email thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/resend-verify")
    public ResponseEntity<?> resendVerify(Principal principal){
        String email = principal.getName();
        authService.resendVerify(email);
        return ResponseEntity.noContent().build();
    }
}
