package com.kkanyo.gf2tool.domain.doll.dto;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.model.Squad;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// DollRequestDto를 재사용하기보다, 등록 전용인 Dto를 만드는 이유는
// 등록할 때 필요한 필드과 결과로 보여줄 필드가 다를 수 있고,
// 등록 시에만 필요한 유효성 검사(@NotBlank 등)를 넣기 위함입니다.
public class DollSaveRequestDto {

    @NotNull
    private String name;
    @NotNull
    private PhaseAttribute attribute;
    @NotNull
    private DollRare rare;
    @NotNull
    private WeaponType weaponType;
    @NotNull
    private Job job;
    @NotNull
    private Squad squad;

    public Doll toEntity() {
        return Doll.builder()
                .name(this.name)
                .attribute(attribute.getValue())
                .rare(rare.getValue())
                .weaponType(weaponType.getValue())
                .job(job.getValue())
                .squad(squad.getValue())
                .build();
    }
}
