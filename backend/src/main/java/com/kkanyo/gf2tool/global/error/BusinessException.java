package com.kkanyo.gf2tool.global.error;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {
    public abstract HttpStatus getStatus();

    public abstract String getErrorCode();
}