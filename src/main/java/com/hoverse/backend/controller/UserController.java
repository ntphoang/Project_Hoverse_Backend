package com.hoverse.backend.controller;

import com.hoverse.backend.dto.user.UserUpdateProfileRequestDTO;
import com.hoverse.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PatchMapping("/me")
    public ResponseEntity<?> updateUserProfile(Principal principal,@RequestBody UserUpdateProfileRequestDTO requestDTO){
        String email = principal.getName();
        return ResponseEntity.ok(userService.updateUserProfile(email,requestDTO));
    }

    @PatchMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadAvatar(Principal principal, @RequestPart(value ="file") MultipartFile file){
        String email = principal.getName();
        return ResponseEntity.ok(userService.uploadAvatar(email,file));
    }
}
