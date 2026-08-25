package com.hoverse.backend.exception;

import com.hoverse.backend.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException ex){
        String message = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("400")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<?> handleDatabaseOperation(DatabaseOperationException ex){
        String message = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("500")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex){
        String message = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("409")
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        String message = "Định dạng dữ liệu đầu vào không hợp lệ. Vui lòng kiểm tra lại kiểu dữ liệu!";

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("400")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String message = "Định dạng dữ liệu đầu vào không hợp lệ. Vui lòng kiểm tra lại kiểu dữ liệu!";

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .time(LocalDateTime.now())
                .message(message)
                .code("400")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }
}
