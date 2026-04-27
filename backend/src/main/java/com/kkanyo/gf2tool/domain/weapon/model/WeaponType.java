package com.kkanyo.gf2tool.domain.weapon.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "무기 타입", enumAsRef = true)
public enum WeaponType {
    HG,
    SMG,
    RF,
    AR,
    MG,
    SG,
    BLD;
}
