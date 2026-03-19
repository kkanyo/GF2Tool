package com.kkanyo.gf2tool.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import com.kkanyo.gf2tool.domain.doll.exception.DollNotFoundException;
import com.kkanyo.gf2tool.domain.doll.exception.DollStatNotFoundException;
import com.kkanyo.gf2tool.global.error.dto.ErrorResponseDto;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class DollExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(DollNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDollNotFound(
            DollNotFoundException e,
            ServletWebRequest request) {
        ErrorResponseDto body = errorResponseFactory.create(
                404,
                e.getMessage(),
                "DOLL_NOT_FOUND",
                request);
        return ResponseEntity.status(404).body(body);
    }

    @ExceptionHandler(DollStatNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDollStatNotFound(
            DollStatNotFoundException e,
            ServletWebRequest request) {
        ErrorResponseDto body = errorResponseFactory.create(
                404,
                e.getMessage(),
                "DOLL_STAT_NOT_FOUND",
                request);
        return ResponseEntity.status(404).body(body);
    }
}

