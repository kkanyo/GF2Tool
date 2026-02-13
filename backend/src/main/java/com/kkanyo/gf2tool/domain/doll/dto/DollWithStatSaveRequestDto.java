package com.kkanyo.gf2tool.domain.doll.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DollWithStatSaveRequestDto {

    private DollSaveRequestDto doll;
    private DollStatSaveRequestDto dollStat;
}
