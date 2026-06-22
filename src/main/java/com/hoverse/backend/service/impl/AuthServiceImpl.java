package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.AuthRequestDTO;
import com.hoverse.backend.dto.AuthResponseDTO;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.security.JwtUtils;
import com.hoverse.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email đã được sử dụng");
        }

        String email = request.getEmail();
        String generatedUsername = email.substring(0,email.indexOf("@"));
        User user = User.builder()
                .username(generatedUsername)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

        String jwtToken = jwtUtils.generateToken(userDetails);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("Không tìm thấy User"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

        String jwtToken = jwtUtils.generateToken(userDetails);

        return AuthResponseDTO.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
