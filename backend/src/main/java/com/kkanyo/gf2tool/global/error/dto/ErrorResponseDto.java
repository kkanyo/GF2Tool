package com.kkanyo.gf2tool.global.error.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
    private String errorCode;
    private Instant timestamp;
    private String path;
}