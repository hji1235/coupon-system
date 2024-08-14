package com.github.hji1235.coupon_system.global;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private Boolean success;

    private T data;

    private ApiError error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDateTime;

    private ApiResponse(Boolean success, T data, ApiError error) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.serverDateTime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success() {
        return ApiResponse.success(null);
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static ApiResponse<?> error(String code, String message) {
        return new ApiResponse<>(false, null, new ApiError(code, message));
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", data=" + data +
                ", apiError=" + error +
                ", serverDateTime=" + serverDateTime +
                '}';
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    protected static class ApiError {

        private String code;

        private String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String toString() {
            return "ApiError{" +
                    "code='" + code + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
