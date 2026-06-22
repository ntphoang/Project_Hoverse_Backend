package com.hoverse.backend.exception;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 22/06/2026
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
