package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.DollStat;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DollStatResponseDto {

    private Long id;
    private Integer attack;
    private Integer defense;
    private Integer health;
    private Integer stability;
    private Integer criticalRate;
    private Integer criticalDamage;
    private Integer mobility;
    private Integer weakness1;
    private Integer weakness2;

    public static DollStatResponseDto fromEntity(DollStat dollStat) {
        return DollStatResponseDto.builder()
                .id(dollStat.getId())
                .attack(dollStat.getAttack())
                .defense(dollStat.getDefense())
                .health(dollStat.getHealth())
                .stability(dollStat.getStability())
                .criticalRate(dollStat.getCriticalRate())
                .criticalDamage(dollStat.getCriticalDamage())
                .mobility(dollStat.getMobility())
                .weakness1(dollStat.getWeakness1())
                .weakness2(dollStat.getWeakness2())
                .build();
    }
}
