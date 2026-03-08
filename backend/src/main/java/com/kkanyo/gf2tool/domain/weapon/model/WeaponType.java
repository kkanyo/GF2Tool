package com.kkanyo.gf2tool.domain.weapon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "무기 타입", enumAsRef = true)
public enum WeaponType {
    HG(1, "권총"),
    SMG(2, "기관단총"),
    RF(3, "소총"),
    AR(4, "돌격소총"),
    MG(5, "기관총"),
    SG(6, "산탄총"),
    BLD(7, "도검");

    private final int value;
    private final String description;
}
