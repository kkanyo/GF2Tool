package com.kkanyo.gf2tool.domain.doll.entity;

import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 객체 생성 방지
@AllArgsConstructor
@Builder
public class DollStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer attack;

    @Column(nullable = false)
    private Integer defense;

    @Column(nullable = false)
    private Integer health;

    @Column(nullable = false)
    private Integer stability;

    @Column(nullable = false)
    private Integer criticalRate;

    @Column(nullable = false)
    private Integer criticalDamage;

    @Column(nullable = false)
    private Integer mobility;

    @Column(nullable = false)
    private Integer attackBonus;

    @Column(nullable = false)
    private Integer defenseBonus;

    @Column(nullable = false)
    private Integer healthBonus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BulletType weakness1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhaseAttribute weakness2;
}
