package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.entity.VerificationToken;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.TokenType;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.repository.VerificationTokenRepository;
import com.hoverse.backend.security.JwtUtils;
import com.hoverse.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 13/06/2026
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailServiceImpl;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        // Kiểm tra email có tồn tại hay chưa
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email đã được sử dụng");
        }

        // Lưu user vào db
        String email = request.getEmail();
        String generatedUsername = email.substring(0,email.indexOf("@"))+"_"+System.currentTimeMillis();
        User user = User.builder()
                .username(generatedUsername)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();

        // Xác thực email
        String emailToken = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .type(TokenType.VERIFY_EMAIL)
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .token(emailToken)
                .user(user)
                .build();

        user.setVerificationToken(verificationToken);
        userRepository.save(user);

        emailServiceImpl.sendVerificationEmail(email,emailToken);
        return AuthResponseDTO.builder()
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
                .id(user.getId())
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .isEmailVerified(user.isEmailVerified())
                .build();
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy token: "+token));

        if(verificationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            verificationTokenRepository.deleteById(verificationToken.getId());
            throw new RuntimeException("Token đã hết hạn. Vui lòng đăng ký lại hoặc yêu cầu gửi mail mới!");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        user.setVerificationToken(null);
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public void resendVerify(String email) {
        if(email==null){
            throw new BadRequestException("Tài khoản không hợp lệ!");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));

        VerificationToken tokenOld = user.getVerificationToken();

        if(tokenOld!=null && tokenOld.getExpiredAt().isAfter(LocalDateTime.now())){
            throw new BadRequestException("Email xác thực cũ vẫn còn hiệu lực, vui lòng thử lại sau!");
        }

        if(tokenOld!=null && tokenOld.getExpiredAt().isBefore(LocalDateTime.now())){
            user.setVerificationToken(null);
            verificationTokenRepository.delete(tokenOld);
        }

        String emailToken = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .type(TokenType.VERIFY_EMAIL)
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .token(emailToken)
                .user(user)
                .build();

        user.setVerificationToken(verificationToken);
        userRepository.save(user);

        emailServiceImpl.sendVerificationEmail(user.getEmail(),emailToken);
    }
}
