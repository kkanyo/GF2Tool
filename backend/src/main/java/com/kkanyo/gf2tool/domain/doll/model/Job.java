package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "직업", enumAsRef = true)
public enum Job {
    SENTINEL(1, "센티널"),
    VANGUARD(2, "뱅가드"),
    SUPPORT(3, "서포트"),
    BULWARK(4, "불워크");

    private final int value;
    private final String description;
}
