package com.kkanyo.gf2tool.domain.weapon.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "무기 속성", enumAsRef = true)
public enum BulletType {
    LIGHT_AMMO,
    MEDIUM_AMMO,
    HEAVY_AMMO,
    SHOTGUN_AMMO,
    MELEE;
}
