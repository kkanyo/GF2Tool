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
public class DollResponseDto {

    private String name;
    private Integer attribute1;
    private Integer attribute2;
    private Integer rare;
    private Integer weaponType;
    private Integer job;
    private Integer squad;

    public static DollResponseDto fromEntity(Doll doll) {
        return DollResponseDto.builder()
                .name(doll.getName())
                .attribute1(doll.getAttribute1())
                .attribute2(doll.getAttribute2())
                .rare(doll.getRare())
                .weaponType(doll.getWeaponType())
                .job(doll.getJob())
                .squad(doll.getSquad())
                .build();
    }
}
