package com.hoverse.backend.exception;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 14/08/2026
 */
public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
