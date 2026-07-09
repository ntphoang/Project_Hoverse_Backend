package com.hoverse.backend.dto;

import com.hoverse.backend.enums.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 09/07/2026
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CloudinaryUploadResponseDTO {
    private String url;
    private String publicId;
    private MediaType type;
}
