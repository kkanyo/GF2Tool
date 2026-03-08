package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "위상 속성", enumAsRef = true)
public enum PhaseAttribute {
    NONE(0, "없음"),
    BURN(1, "화염"),
    ELECTRIC(2, "전도"),
    HYDRO(3, "탁류"),
    CORROSION(4, "산성"),
    FREEZE(5, "빙결"),
    PHYSICAL(6, "물리");

    private final int value;
    private final String description;
}
