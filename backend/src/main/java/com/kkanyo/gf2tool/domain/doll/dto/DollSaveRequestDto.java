package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// DollRequestDto를 재사용하기보다, 등록 전용인 Dto를 만드는 이유는
// 등록할 때 필요한 필드과 결과로 보여줄 필드가 다를 수 있고,
// 등록 시에만 필요한 유효성 검사(@NotBlank 등)를 넣기 위함입니다.
public class DollSaveRequestDto {

    private String name;
    private Integer attribute1;
    private Integer attribute2;
    private Integer rare;
    private Integer weaponType;
    private Integer job;
    private Integer squad;

    public Doll toEntity() {
        return Doll.builder()
                .name(this.name)
                .attribute1(this.attribute1)
                .attribute2(this.attribute2)
                .rare(this.rare)
                .weaponType(this.weaponType)
                .job(this.job)
                .squad(this.squad)
                .build();
    }
}
