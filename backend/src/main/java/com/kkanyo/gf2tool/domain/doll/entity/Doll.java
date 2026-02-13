package com.kkanyo.gf2tool.domain.doll.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 빈 객체 생성 방지
@AllArgsConstructor
@Builder
public class Doll {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    // @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer rare;

    @Column(nullable = false)
    private Integer attribute1; // 탄종

    @Column(nullable = false)
    private Integer attribute2; // 리몰딩 속성

    @Column(nullable = false)
    private Integer weaponType;

    @Column(nullable = false)
    private Integer job;

    @Column(nullable = false)
    private Integer squad;

    // 인형 테이블과 인형 스탯 테이블은 1:1 관계
    // 인형의 상세 정보 페이지 진입 전까진 스탯 정보는 불러오지 않는다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doll_stat_id")
    private DollStat dollStat;
}
