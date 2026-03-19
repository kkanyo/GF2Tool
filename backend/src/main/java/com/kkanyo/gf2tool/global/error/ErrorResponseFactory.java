package com.kkanyo.gf2tool.global.error;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.kkanyo.gf2tool.global.error.dto.ErrorResponseDto;

@Component
public class ErrorResponseFactory {

    public ErrorResponseDto create(
            int status,
            String message,
            String errorCode,
            ServletWebRequest request) {
        String path = request == null ? null : request.getRequest().getRequestURI();
        return new ErrorResponseDto(status, message, errorCode, Instant.now(), path);
    }
}

