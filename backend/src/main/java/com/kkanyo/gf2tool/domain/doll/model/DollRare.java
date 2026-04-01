package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인형 레어도", enumAsRef = true)
public enum DollRare {
    STANDARD,
    ELITE
}