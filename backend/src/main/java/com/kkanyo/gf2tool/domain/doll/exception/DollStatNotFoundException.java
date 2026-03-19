package com.kkanyo.gf2tool.domain.doll.exception;

import lombok.Getter;

@Getter
public class DollStatNotFoundException extends RuntimeException {
    private final Long dollId;

    public DollStatNotFoundException(Long dollId) {
        super(String.format("Doll stat not found. [id:%d]", dollId));
        this.dollId = dollId;
    }
}
