package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DollStatSaveRequestDto {

    @NotNull
    private Integer attack;
    @NotNull
    private Integer defense;
    @NotNull
    private Integer health;
    @NotNull
    private Integer stability;
    @NotNull
    private Integer criticalRate;
    @NotNull
    private Integer criticalDamage;
    @NotNull
    private Integer mobility;
    @NotNull
    private Integer attackBonus;
    @NotNull
    private Integer defenseBonus;
    @NotNull
    private Integer healthBonus;
    @NotNull
    private BulletType weakness1;
    @NotNull
    private PhaseAttribute weakness2;

    public DollStat toEntity() {
        return DollStat.builder()
                .attack(this.attack)
                .defense(this.defense)
                .health(this.health)
                .stability(this.stability)
                .criticalRate(this.criticalRate)
                .criticalDamage(this.criticalDamage)
                .mobility(this.mobility)
                .attackBonus(this.attackBonus)
                .defenseBonus(this.defenseBonus)
                .healthBonus(this.healthBonus)
                .weakness1(this.weakness1.getValue())
                .weakness2(this.weakness2.getValue())
                .build();
    }
}
