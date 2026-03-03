package com.lhj.myoppingmall.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhj.myoppingmall.global.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException{

        ApiResponseDto<Void> body =
                ApiResponseDto.fail(
                        ErrorCode.UNAUTHORIZED.getStatus(),
                        ErrorCode.UNAUTHORIZED.getMessage()
                );

        response.setStatus(ErrorCode.UNAUTHORIZED.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }
}
