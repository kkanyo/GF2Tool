package com.kkanyo.gf2tool.domain.doll.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Integer weakness1;

    @Column(nullable = false)
    private Integer weakness2;
}
