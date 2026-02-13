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
    private Integer rare;
    private Integer attribute1;
    private Integer attribute2;
    private Integer weaponType;
    private Integer job;
    private Integer squad;
    private Long dollStatId;

    public static DollSaveResponseDto fromEntity(Doll doll) {
        return DollSaveResponseDto.builder()
                .id(doll.getId())
                .name(doll.getName())
                .rare(doll.getRare())
                .attribute1(doll.getAttribute1())
                .attribute2(doll.getAttribute2())
                .weaponType(doll.getWeaponType())
                .job(doll.getJob())
                .squad(doll.getSquad())
                .dollStatId(doll.getDollStat() != null ? doll.getDollStat().getId() : null)
                .build();
    }
}
