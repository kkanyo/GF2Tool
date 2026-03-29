package com.kkanyo.gf2tool.global.error;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import com.kkanyo.gf2tool.global.error.dto.ErrorResponseDto;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            ServletWebRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .limit(5)
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        if (message.isBlank()) {
            message = "Validation failed";
        }

        ErrorResponseDto body = errorResponseFactory.create(
                HttpStatus.BAD_REQUEST,
                message,
                "VALIDATION_ERROR",
                request);
        return ResponseEntity.badRequest().body(body);
    }
}
