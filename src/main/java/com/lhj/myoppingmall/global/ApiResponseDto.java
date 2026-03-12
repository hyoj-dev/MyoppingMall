package com.lhj.myoppingmall.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
* 모든 API 응답 형식 통일 위한 API 응답 클래스
* */
@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {

    @Schema(description = "HTTP 상태 코드", example = "200")
    private int status;

    @Schema(description = "메시지", example = "요청에 성공했습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    // 200 OK
    public static <T> ApiResponseDto<T> ok(T data) {
        return new ApiResponseDto<>(HttpStatus.OK.value(), "OK", data);
    }

    public static <T> ApiResponseDto<T> ok(String message, T data) {
        return new ApiResponseDto<>(HttpStatus.OK.value(), message, data);
    }

    // 201 CREATED
    public static <T> ApiResponseDto<T> created(String message, T data) {
        return new ApiResponseDto<>(HttpStatus.CREATED.value(), message, data);
    }

    //실패 응답
    public static ApiResponseDto<Void> fail(HttpStatus status, String message) {
        return new ApiResponseDto<>(status.value(), message, null);
    }
}
