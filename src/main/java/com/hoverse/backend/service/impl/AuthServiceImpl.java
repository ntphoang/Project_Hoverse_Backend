package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.user.AuthRequestDTO;
import com.hoverse.backend.dto.user.AuthResponseDTO;
import com.hoverse.backend.dto.user.AuthResultDTO;
import com.hoverse.backend.entity.RefreshToken;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.entity.VerificationToken;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.TokenType;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.repository.RefreshTokenRepository;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.repository.VerificationTokenRepository;
import com.hoverse.backend.security.JwtUtils;
import com.hoverse.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh.expiration}")
    private int refreshExpiration;

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        // Kiểm tra email có tồn tại hay chưa
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestException("Email đã được sử dụng");
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

        try {
            emailServiceImpl.sendVerificationEmail(email,emailToken);
        } catch (MailSendException e) {
            throw new BadRequestException("Email không tồn tại hoặc không sử dụng được. Vui lòng sử dụng email khac!");
        }

        return AuthResponseDTO.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Override
    @Transactional
    public AuthResultDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("Không tìm thấy User"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()))
        );

        String jwtToken = jwtUtils.generateToken(userDetails);

        String refreshTokenString = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .isActive(true)
                .expiredAt(LocalDateTime.now().plus(Duration.ofMillis(refreshExpiration)))
                .token(refreshTokenString)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);

        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .id(user.getId())
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .avatarUrl(user.getAvatarUrl())
                .isEmailVerified(user.isEmailVerified())
                .build();

        return AuthResultDTO.builder()
                .responseDTO(responseDTO)
                .refreshToken(refreshTokenString)
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

    @Override
    public AuthResponseDTO refreshToken(String refreshTokenString) {
       RefreshToken refreshTokenRepo = refreshTokenRepository.findRefreshTokenByToken(refreshTokenString);

       if(refreshTokenRepo == null){
           throw new BadRequestException("Token không hợp lệ hoặc đã bị đăng xuất!");
       }

       if(refreshTokenRepo.getExpiredAt().isBefore(LocalDateTime.now())){
           refreshTokenRepository.delete(refreshTokenRepo);
           throw new BadRequestException("Phiên đăng nhập hết hạn, vui lòng login lại!");
       }

       if(!refreshTokenRepo.getIsActive()){
           throw new BadRequestException("Token đã bị thu hồi!");
       }

       User user = refreshTokenRepo.getUser();
       UserDetails userDetails = new org.springframework.security.core.userdetails.User(
               user.getEmail(),
               user.getPassword(),
               Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()))
       );
       String newToken = jwtUtils.generateToken(userDetails);

       return AuthResponseDTO.builder()
               .token(newToken)
               .build();
    }
}
