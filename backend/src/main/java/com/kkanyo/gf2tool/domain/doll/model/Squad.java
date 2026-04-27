package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "소속", enumAsRef = true)
public enum Squad {
    NONE,
    GROZA_SQUAD,
    ELMO_INTEGRATED_SQUAD,
    ZUCCHERO_CAFE,
    FROSTFALL_SQUAD,
    POL03_SECURITY_DIVISIONS,
    DOLL_COMMUNITY,
    MONSOON,
    HIDE404,
    HIDE404_TEAM1,
    HIDE404_TEAM2,
    SPARK_SQUAD,
    TEAM_AR,
    REBELLION,
    LAB16,
    STATE_SECURITY_AGENCY;
}