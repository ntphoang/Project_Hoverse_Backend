package com.hoverse.backend.service;

import com.hoverse.backend.dto.CloudinaryUploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 08/07/2026
 */
public interface CloudinaryService {
     CloudinaryUploadResponseDTO uploadFile(MultipartFile file);
}
