package com.kkanyo.gf2tool.global.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import com.kkanyo.gf2tool.global.error.dto.ErrorResponseDto;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleInternalServerError(
            Exception e,
            ServletWebRequest request) {
        log.error("Unhandled exception", e);

        ErrorResponseDto body = errorResponseFactory.create(
                500,
                "Internal Server Error",
                "INTERNAL_SERVER_ERROR",
                request);
        return ResponseEntity.status(500).body(body);
    }
}