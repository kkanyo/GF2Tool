package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "직업", enumAsRef = true)
public enum Job {
    SENTINEL,
    VANGUARD,
    SUPPORT,
    BULWARK;
}
