package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.UserProfileResponseDTO;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.UserMapper;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 21/07/2026
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileResponseDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        return userMapper.toResponseDTO(user);
    }
}
