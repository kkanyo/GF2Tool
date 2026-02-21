package com.kkanyo.gf2tool.domain.doll.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DollSearchCondition {

    private String name;
    private Integer attribute2; // 리몰딩 속성
    private Integer rare;
    private Integer weaponType;
    private Integer job;
}
