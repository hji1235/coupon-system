package com.github.hji1235.coupon_system.global.error;

import com.github.hji1235.coupon_system.global.ApiResponse;
import com.github.hji1235.coupon_system.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        log.error("BusinessException exception occurred : {}", e.getMessage(), e);
        return newResponseEntity(e.getErrorCode().getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> newResponseEntity(String code, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(ApiResponse.error(code, message));
    }
}
