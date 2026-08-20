package com.hoverse.backend.service.impl;

import com.hoverse.backend.dto.cloudinary.CloudinaryUploadResponseDTO;
import com.hoverse.backend.dto.user.*;
import com.hoverse.backend.entity.RefreshToken;
import com.hoverse.backend.entity.User;
import com.hoverse.backend.enums.Role;
import com.hoverse.backend.enums.UserStatus;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.CloudinaryUploadException;
import com.hoverse.backend.exception.DatabaseOperationException;
import com.hoverse.backend.exception.ResourceNotFoundException;
import com.hoverse.backend.mapper.UserMapper;
import com.hoverse.backend.repository.UserRepository;
import com.hoverse.backend.repository.specification.UserSpecification;
import com.hoverse.backend.service.CloudinaryService;
import com.hoverse.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

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
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public UserProfileResponseDTO changePassword(String email, UserChangePasswordRequestDTO requestDTO) {
        User user = userRepository.findByEmailAndStatus(email,UserStatus.ACTIVE)
                .orElseThrow(()->new ResourceNotFoundException("Không tìm thấy user với email: "+email+" hoặc tài khoản đã bị khóa!"));

        if(!passwordEncoder.matches(requestDTO.getOldPassword(),user.getPassword())){
            throw new BadRequestException("Mật khẩu cũ không đúng. Vui lòng thử lại!");
        }

        if(requestDTO.getOldPassword().equals(requestDTO.getNewPassword())){
            throw new BadRequestException("Vui lòng nhâp mật khẩu mới khác với mật khẩu cũ!");
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserProfileResponseDTO changeUserStatus(String email, Long userId, UserChangeStatusRequestDTO requestDTO) {
        User user = userRepository.findByEmailAndStatus(email,UserStatus.ACTIVE)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy user với email: "+email+" hoặc tài khoản đã bị khóa!"));

        User userChange = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Không tim thấy user với id: "+ userId+" hoặc tài khoản đã bị xóa!"));

        if(userChange.getRole() == Role.ADMIN){
            throw new BadRequestException("Không được phép thay đổi trạng thái của admin!");
        }

        if(userChange.getStatus() == requestDTO.getStatus()){
            throw new BadRequestException("Tài khoản này đang ở trạng thái "+ requestDTO.getStatus()+" rồi!");
        }

        userChange.setStatus(requestDTO.getStatus());

        if(userChange.getStatus().equals(UserStatus.BANNED) || userChange.getStatus().equals(UserStatus.DELETED)) {
            userChange.setReason(requestDTO.getReason());

            Set<RefreshToken> refreshTokens = userChange.getRefreshTokens();
            refreshTokens.forEach(refreshToken -> refreshToken.setIsActive(false));
        }else{
            userChange.setReason(null);
        }

        return userMapper.toResponseDTO(userChange);
    }

    @Override
    public Page<UserProfileResponseDTO> getUserByConditions(UserFilterRequestDTO requestDTO, Pageable pageable) {
        Specification<User> specification =
                Specification.where(UserSpecification.hasStatus(requestDTO.getStatus()))
                        .and(UserSpecification.hasRole(Role.USER));

        Page<User> users = userRepository.findAll(specification, pageable);
        return users.map(userMapper::toResponseDTO);
    }

    @Override
    public List<UserCountResponseDTO> countUsersGroupByMonth(int year) {
        List<Object[]> repoList = userRepository.countUsersGroupByMonth(year);

        Map<Integer, Long> countMap= new HashMap<>();
        for (Object[] object : repoList) {
            countMap.put((Integer) object[0],(Long) object[1]);
        }

        List<UserCountResponseDTO> responseDTOs = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            UserCountResponseDTO userCountResponseDTO = UserCountResponseDTO.builder()
                    .month(i)
                    .count(countMap.getOrDefault(i, 0L))
                    .build();
            responseDTOs.add(userCountResponseDTO);
        }

        return responseDTOs;
    }
}
