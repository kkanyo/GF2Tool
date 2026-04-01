package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "위상 속성", enumAsRef = true)
public enum PhaseAttribute {
    NONE,
    BURN,
    ELECTRIC,
    HYDRO,
    CORROSION,
    FREEZE,
    PHYSICAL;
}
