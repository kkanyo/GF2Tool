package com.kkanyo.gf2tool.domain.weapon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "무기 속성", enumAsRef = true)
public enum BulletType {
    LIGHT_AMMO(1, "경량탄"),
    MEDIUM_AMMO(2, "표준탄"),
    HEAVY_AMMO(3, "중량탄"),
    SHOTGUN_AMMO(4, "산탄"),
    MELEE(5, "근접");

    private final int value;
    private final String description;
}
