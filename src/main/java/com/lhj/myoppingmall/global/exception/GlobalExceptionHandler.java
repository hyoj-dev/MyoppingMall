package com.lhj.myoppingmall.global.exception;

import com.lhj.myoppingmall.global.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //비즈니스 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        log.warn("[BUSINESS] status={}, msg={}", errorCode.getStatus(), e.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponseDto.fail(
                        errorCode.getStatus(),
                        e.getMessage()
                ));
    }

    //DTO @Valid 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getField() + ":" + fe.getDefaultMessage())
                .orElse(ErrorCode.INVALID_INPUT.getMessage());

        log.warn("[VALIDATION] msg={}", msg);

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(ApiResponseDto.fail(
                        ErrorCode.INVALID_INPUT.getStatus(), msg
                ));
    }

    // ID(Long)에 다른 타입이 들어온다면
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {

        log.warn("[TYPE_MISMATCH] name={}, value={}", e.getName(), e.getValue());

        return ResponseEntity
                .status(ErrorCode.TYPE_MISMATCH.getStatus())
                .body(ApiResponseDto.fail(
                        ErrorCode.TYPE_MISMATCH.getStatus(),
                        ErrorCode.TYPE_MISMATCH.getMessage()
                ));
    }

    //최후의 보루
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleException(Exception e) {
        log.error("[ERROR] msg={}", e.getMessage(), e);

        return ResponseEntity
                .status(ErrorCode.INTERNAL_ERROR.getStatus())
                .body(ApiResponseDto.fail(
                        ErrorCode.INTERNAL_ERROR.getStatus(),
                        ErrorCode.INTERNAL_ERROR.getMessage()
                ));
    }
}
