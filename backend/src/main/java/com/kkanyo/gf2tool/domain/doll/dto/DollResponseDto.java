package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DollResponseDto {

    private Long id;
    private String name;
    private PhaseAttribute attribute;
    private DollRare rare;
    private WeaponType weaponType;
    private Job job;

    @QueryProjection
    public DollResponseDto(Long id, String name, PhaseAttribute attribute, DollRare rare, WeaponType weaponType,
            Job job) {
        this.id = id;
        this.name = name;
        this.attribute = attribute;
        this.rare = rare;
        this.weaponType = weaponType;
        this.job = job;
    }

    public static DollResponseDto fromEntity(Doll doll) {
        return DollResponseDto.builder()
                .name(doll.getName())
                .attribute(doll.getAttribute())
                .rare(doll.getRare())
                .weaponType(doll.getWeaponType())
                .job(doll.getJob())
                .build();
    }
}
