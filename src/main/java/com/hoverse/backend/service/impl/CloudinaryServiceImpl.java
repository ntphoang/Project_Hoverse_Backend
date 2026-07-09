package com.hoverse.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hoverse.backend.dto.CloudinaryUploadResponseDTO;
import com.hoverse.backend.enums.MediaType;
import com.hoverse.backend.exception.BadRequestException;
import com.hoverse.backend.exception.CloudinaryUploadException;
import com.hoverse.backend.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;

    private CloudinaryUploadResponseDTO toResponseDTO(Map<String,Object> response){
        String resourceType =(String) response.get("resource_type");

        MediaType mediaType;

        switch (resourceType){
            case "image" -> mediaType = MediaType.IMAGE;
            case "video" -> mediaType = MediaType.VIDEO;
            default -> throw new IllegalArgumentException("Unsupported resource type: "+resourceType);
        }

        return CloudinaryUploadResponseDTO.builder()
                .url((String) response.get("secure_url"))
                .publicId((String) response.get("public_id"))
                .type(mediaType)
                .build();
    }

    @Override
    public CloudinaryUploadResponseDTO uploadFile(MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new BadRequestException("Vui lòng chọn file để tải lên!");
        }
        try {
            byte[] fileBytes = file.getBytes();

            Map<String,Object> response = cloudinary.uploader().upload(
                    fileBytes,
                    ObjectUtils.asMap("folder","hoverse/places")
            );

            return toResponseDTO(response);
        } catch (IOException e) {
            throw new CloudinaryUploadException("Tải ảnh lên Cloudinary thất bại!",e);
        }
    }
}
