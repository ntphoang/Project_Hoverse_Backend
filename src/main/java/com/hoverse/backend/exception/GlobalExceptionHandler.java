package com.hoverse.backend.exception;

import com.hoverse.backend.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Project_Hoverse_Backend
 * Author: Phi Hoàng
 * Date: 22/06/2026
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex){
        String message = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("404")
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }
}
