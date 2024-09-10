package com.github.hji1235.coupon_system.global.error;

import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        log.error("BusinessException exception occurred : {}", e.getMessage(), e);
        return newResponseEntity(e.getErrorCode().getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        String fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        String globalErrors = e.getBindingResult().getGlobalErrors().stream()
                .map(error -> String.format("%s", error.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        StringJoiner joiner = new StringJoiner(", ");
        if (!fieldErrors.isEmpty()) {
            joiner.add(fieldErrors);
        }
        if (!globalErrors.isEmpty()) {
            joiner.add(globalErrors);
        }
        String errorMessage = String.format("%s : %s", errorCode.getMessage(), joiner);
        return newResponseEntity(errorCode.getCode(), errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> newResponseEntity(String code, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(code, message));
    }
}
