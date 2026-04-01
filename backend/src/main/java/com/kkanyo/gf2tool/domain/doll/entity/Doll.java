package com.kkanyo.gf2tool.domain.doll.entity;

import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.model.Squad;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Doll {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DollRare rare;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhaseAttribute attribute; // 리몰딩 속성

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WeaponType weaponType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Squad squad;

    // 인형 테이블과 인형 스탯 테이블은 1:1 관계
    // 인형의 상세 정보 페이지 진입 전까진 스탯 정보는 불러오지 않는다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doll_stat_id")
    private DollStat dollStat;

    public void setDollStat(DollStat dollStat) {
        this.dollStat = dollStat;
    }
}
