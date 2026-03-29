package com.kkanyo.gf2tool.domain.doll.exception;

import org.springframework.http.HttpStatus;

import com.kkanyo.gf2tool.global.error.BusinessException;

import lombok.Getter;

@Getter
public class DollNotFoundException extends BusinessException {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getErrorCode() {
        return "DOLL_NOT_FOUND";

    }
}
