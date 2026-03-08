package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "인형 레어도", enumAsRef = true)
public enum DollRare {
    STANDARD(1, "표준"),
    ELITE(2, "엘리트");

    private final int value;
    private final String description;
}