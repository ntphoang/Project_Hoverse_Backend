package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.cloudinary.CloudinaryUploadResponseDTO;
import com.hoverse.backend.dto.user.UserProfileResponseDTO;
import com.hoverse.backend.dto.user.UserUpdateProfileRequestDTO;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.exception.CloudinaryUploadException;
import com.hoverse.backend.exception.DatabaseOperationException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.UserMapper;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.service.CloudinaryService;
import com.hoverse.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final CloudinaryService cloudinaryService;

    @Override
    public UserProfileResponseDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserProfileResponseDTO updateUserProfile(String email, UserUpdateProfileRequestDTO requestDTO) {
        User user = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy user với email: "+email+" hoặc tài khoản đã bị khóa!"));

        user.setFullName(requestDTO.getFullName());

        User userSaved = userRepository.save(user);
        return userMapper.toResponseDTO(userSaved);
    }

    @Override
    public UserProfileResponseDTO uploadAvatar(String email, MultipartFile file) {
        User user = userRepository.findByEmailAndStatus(email,UserStatus.ACTIVE)
                .orElseThrow(()->new ResourceNotFoundException("Không tim  thấy user với email: "+ email+" hoặc tài khoản đã bị khóa!"));

        CloudinaryUploadResponseDTO cloudinaryUploadResponseDTO;
        try {
             cloudinaryUploadResponseDTO = cloudinaryService.uploadFile(file,"/users");
        } catch (Exception e) {
            throw new CloudinaryUploadException("Tải ảnh lên cloud thất bại!",e);
        }

        String avatarUrlOld = user.getAvatarUrl();
        String publicIdOld = user.getPublicId();
        try {
            user.setAvatarUrl(cloudinaryUploadResponseDTO.getUrl());
            user.setPublicId(cloudinaryUploadResponseDTO.getPublicId());
            User userSaved = userRepository.save(user);
            if(publicIdOld!=null)cloudinaryService.deleteFile(publicIdOld);
            return userMapper.toResponseDTO(userSaved);
        } catch (Exception e) {
            user.setAvatarUrl(avatarUrlOld);
            user.setPublicId(publicIdOld);
            cloudinaryService.deleteFile(cloudinaryUploadResponseDTO.getPublicId());
            throw new DatabaseOperationException("Lưu ảnh vào database thất bại!");
        }
    }
}
