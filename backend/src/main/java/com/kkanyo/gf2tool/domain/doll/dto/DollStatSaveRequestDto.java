package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.DollStat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DollStatSaveRequestDto {

    private Integer attack;
    private Integer defense;
    private Integer health;
    private Integer stability;
    private Integer criticalRate;
    private Integer criticalDamage;
    private Integer mobility;
    private Integer weakness1;
    private Integer weakness2;

    public DollStat toEntity() {
        return DollStat.builder()
                .attack(this.attack)
                .defense(this.defense)
                .health(this.health)
                .stability(this.stability)
                .criticalRate(this.criticalRate)
                .criticalDamage(this.criticalDamage)
                .mobility(this.mobility)
                .weakness1(this.weakness1)
                .weakness2(this.weakness2)
                .build();
    }
}
