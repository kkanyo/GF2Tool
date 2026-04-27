package com.kkanyo.gf2tool.domain.doll.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.exception.DollNotFoundException;
import com.kkanyo.gf2tool.domain.doll.exception.DollStatNotFoundException;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.model.Squad;
import com.kkanyo.gf2tool.domain.doll.repository.DollRepository;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;

@ExtendWith(MockitoExtension.class)
class DollServiceTest {

    @Mock
    private DollRepository dollRepository;

    @InjectMocks
    private DollService dollService;

    // ────────────────────────────── Test Helper ──────────────────────────────

    private static DollStat buildDollStat() {
        return DollStat.builder()
                .attack(100).defense(50).health(500).stability(10)
                .criticalRate(20).criticalDamage(120).mobility(6)
                .attackBonus(10).defenseBonus(0).healthBonus(0)
                .weakness1(BulletType.HEAVY_AMMO).weakness2(PhaseAttribute.BURN)
                .build();
    }

    private static Doll buildDoll(DollStat stat) {
        return Doll.builder()
                .name("Groza")
                .attribute(PhaseAttribute.PHYSICAL).rare(DollRare.STANDARD).weaponType(WeaponType.AR).job(Job.BULWARK)
                .squad(Squad.GROZA_SQUAD)
                .dollStat(stat)
                .build();
    }

    // ────────────────────────────── findAll ──────────────────────────────

    @Test
    @DisplayName("findAll: 인형 목록을 Page<DollResponseDto>로 변환하여 반환한다")
    void findAll() {
        // given
        Doll doll = buildDoll(buildDollStat());
        Page<Doll> dollPage = new PageImpl<>(List.of(doll), PageRequest.of(0, 10), 1);
        given(dollRepository.findAll(any(PageRequest.class))).willReturn(dollPage);

        // when
        Page<DollResponseDto> result = dollService.findAll(PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Groza");
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    // ───────────────────────── getDollStatByDollId ──────────────────────────

    @Test
    @DisplayName("getDollStatByDollId: 정상 조회 시 DollStatResponseDto 의 스탯 값이 정확하다")
    void getDollStatByDollId() {
        // given
        DollStat stat = buildDollStat();
        given(dollRepository.findById(1L)).willReturn(Optional.of(buildDoll(stat)));

        // when
        DollStatResponseDto result = dollService.getDollStatByDollId(1L);

        // then
        assertThat(result.getAttack()).isEqualTo(100);
        assertThat(result.getDefense()).isEqualTo(50);
        assertThat(result.getHealth()).isEqualTo(500);
        assertThat(result.getMobility()).isEqualTo(6);
    }

    @Test
    @DisplayName("getDollStatByDollId: 인형이 존재하지 않으면 DollNotFoundException 이 발생한다")
    void getDollStatByDollIdDollNotFoundException() {
        // given
        given(dollRepository.findById(999L)).willReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> dollService.getDollStatByDollId(999L))
                .isInstanceOf(DollNotFoundException.class);
    }

    @Test
    @DisplayName("getDollStatByDollId: dollStat 이 null 이면 DollStatNotFoundException 이 발생한다")
    void getDollStatByDollIdDollStatNotFoundException() {
        // given — stat 없이 Doll 을 생성
        Doll dollWithoutStat = Doll.builder()
                .name("Groza").attribute(PhaseAttribute.PHYSICAL).rare(DollRare.STANDARD).weaponType(WeaponType.AR)
                .job(Job.BULWARK).squad(Squad.GROZA_SQUAD)
                .build();
        given(dollRepository.findById(1L)).willReturn(Optional.of(dollWithoutStat));

        // when / then
        assertThatThrownBy(() -> dollService.getDollStatByDollId(1L))
                .isInstanceOf(DollStatNotFoundException.class);
    }

    // ────────────────────────────── save ──────────────────────────────

    @Test
    @DisplayName("save: dollRepository.save 가 호출되고 DollSaveResponseDto 가 반환된다")
    void save_정상저장() {
        // given
        DollStat stat = buildDollStat();
        Doll doll = buildDoll(stat);

        DollSaveRequestDto dollReqDto = mock(DollSaveRequestDto.class);
        DollStatSaveRequestDto statReqDto = mock(DollStatSaveRequestDto.class);
        given(dollReqDto.toEntity()).willReturn(doll);
        given(statReqDto.toEntity()).willReturn(stat);
        given(dollRepository.save(any(Doll.class))).willReturn(doll);

        // when
        DollSaveResponseDto result = dollService.save(dollReqDto, statReqDto);

        // then
        assertThat(result.getName()).isEqualTo("Groza");
        then(dollRepository).should(times(1)).save(any(Doll.class));
    }
}
