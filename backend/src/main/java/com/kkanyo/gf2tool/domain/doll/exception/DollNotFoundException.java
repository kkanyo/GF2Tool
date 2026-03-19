package com.kkanyo.gf2tool.domain.doll.exception;

import lombok.Getter;

@Getter
public class DollNotFoundException extends RuntimeException {

    private final Long dollId;

    public DollNotFoundException(Long dollId) {
        super(String.format("Doll not found. [id:%d]", dollId));
        this.dollId = dollId;
    }
}
