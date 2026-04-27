package com.kkanyo.gf2tool.global.error.dto;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private HttpStatus status;
    private String message;
    private String errorCode;
    private Instant timestamp;
    private String path;
}