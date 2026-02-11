package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.DollStat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DollStatResponseDto {

    private Integer attack;
    private Integer defense;
    private Integer health;
    private Integer stability;
    private Integer criticalRate;
    private Integer criticalDamage;
    private Integer mobility;
    private Integer weakness1;
    private Integer weakness2;

    public DollStatResponseDto(DollStat stat) {
        this.attack = stat.getAttack();
        this.defense = stat.getDefense();
        this.health = stat.getHealth();
        this.stability = stat.getStability();
        this.criticalRate = stat.getCriticalRate();
        this.criticalDamage = stat.getCriticalDamage();
        this.mobility = stat.getMobility();
        this.weakness1 = stat.getWeakness1();
        this.weakness2 = stat.getWeakness2();
    }

}
