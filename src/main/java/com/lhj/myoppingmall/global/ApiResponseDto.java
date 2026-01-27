package com.lhj.myoppingmall.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.query.Param;

/*
* 모든 API 응답 형식 통일 위한 API 응답 클래스
* */
@Getter
@AllArgsConstructor
public class ApiResponseDto<T> {
    private int status;

    private String massage;

    private T data;

}
