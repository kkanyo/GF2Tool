package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DollStatSaveRequestDto {

    @NotNull
    @Min(0)
    private Integer attack;
    @NotNull
    @Min(0)
    private Integer defense;
    @NotNull
    @Min(1)
    private Integer health;
    @NotNull
    @Min(0)
    private Integer stability;
    @NotNull
    @Min(0)
    private Integer criticalRate;
    @NotNull
    @Min(0)
    private Integer criticalDamage;
    @NotNull
    @Min(0)
    private Integer mobility;
    @NotNull
    @Min(0)
    private Integer attackBonus;
    @NotNull
    @Min(0)
    private Integer defenseBonus;
    @NotNull
    @Min(0)
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
