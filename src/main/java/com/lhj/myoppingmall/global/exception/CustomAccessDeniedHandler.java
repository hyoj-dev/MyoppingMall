package com.lhj.myoppingmall.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhj.myoppingmall.global.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {

        ApiResponseDto<Void> body =
                ApiResponseDto.fail(
                        ErrorCode.FORBIDDEN.getStatus(),
                        ErrorCode.FORBIDDEN.getMessage()
                );

        response.setStatus(ErrorCode.FORBIDDEN.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }
}
