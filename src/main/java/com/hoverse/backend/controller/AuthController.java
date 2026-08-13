package com.hoverse.backend.controller;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;
import com.hoverse.backend.dto.user.AuthResultDTO;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRequestDTO request){
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
            AuthResultDTO resultDTO = authService.login(request);

            ResponseCookie responseCookie = ResponseCookie
                    .from("refreshToken", resultDTO.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(Duration.ofDays(7))
                    .path("/")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .body(resultDTO.getResponseDTO());
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

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refreshToken") String refreshTokenString){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenString));
    }
}
