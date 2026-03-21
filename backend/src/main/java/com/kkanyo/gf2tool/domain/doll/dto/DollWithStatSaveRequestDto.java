package com.kkanyo.gf2tool.domain.doll.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DollWithStatSaveRequestDto {

    @Valid
    @NotNull
    private DollSaveRequestDto doll;
    @Valid
    @NotNull
    private DollStatSaveRequestDto dollStat;
}
