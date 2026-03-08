package com.kkanyo.gf2tool.domain.doll.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "소속",enumAsRef = true)
public enum Squad {
    NONE(0, "없음"),
    GROZA_SQUAD(1, "그로자 소대"),
    ELMO_INTEGRATED_SQUAD(2, "엘모호 통합 소대"),
    ZUCCHERO_CAFE(3, "카페 주케로"),
    FROSTFALL_SQUAD(4, "상강 소대"),
    POL03_SECURITY_DIVISIONS(5, "POL-03 치안관리부서"),
    DOLL_COMMUNITY(6, "인형 공동체"),
    MONSOON(7, "계절풍 악단"),
    HIDE404(8, "H.I.D.E. 404"),
    HIDE404_TEAM1(9, "H.I.D.E. 404 1팀"),
    HIDE404_TEAM2(10, "H.I.D.E. 404 2팀"),
    SPARK_SQUAD(11, "스파크 소대"),
    TEAM_AR(12, "AR팀"),
    REBELLION(13, "리벨리온 소대"),
    LAB16(14, "16LAB"),
    STATE_SECURITY_AGENCY(15, "국가안전국");

    private final int value;
    private final String description;
}