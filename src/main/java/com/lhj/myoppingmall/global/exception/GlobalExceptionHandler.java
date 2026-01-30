package com.lhj.myoppingmall.global.exception;

import com.lhj.myoppingmall.global.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ApiResponseDto<>(
                        errorCode.getStatus().value(),
                        errorCode.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(500)
                .body(new ApiResponseDto<>(
                        500,
                        "서버 오류가 발생했습니다.",
                        null
                ));
    }
}
