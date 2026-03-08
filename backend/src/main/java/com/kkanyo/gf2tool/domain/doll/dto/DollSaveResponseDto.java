package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DollSaveResponseDto {

    private Long id;
    private String name;
    private Long dollStatId;

    public static DollSaveResponseDto fromEntity(Doll doll) {
        return DollSaveResponseDto.builder()
                .id(doll.getId())
                .name(doll.getName())
                .dollStatId(doll.getDollStat() != null ? doll.getDollStat().getId() : null)
                .build();
    }
}
