package com.hoverse.backend.exception;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 09/07/2026
 */
public class CloudinaryUploadException extends RuntimeException{
    public CloudinaryUploadException(String message, Throwable throwable){
        super(message,throwable);
    }
}
