package com.kkanyo.gf2tool.domain.doll.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSearchCondition;
import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.model.Squad;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;
import com.kkanyo.gf2tool.global.config.QuerydslConfig;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class DollRepositoryImplTest {

    @Autowired
    private DollRepository dollRepository;

    // ────────────────────────────── Test Helper ──────────────────────────────

    private static DollStat buildDollStat() {
        return DollStat.builder()
                .attack(100).defense(50).health(500).stability(10)
                .criticalRate(20).criticalDamage(120).mobility(6)
                .attackBonus(10).defenseBonus(0).healthBonus(0)
                .weakness1(BulletType.HEAVY_AMMO).weakness2(PhaseAttribute.BURN)
                .build();
    }

    private static Doll buildDoll(String name, DollRare rare, WeaponType weaponType, Job job) {
        return Doll.builder()
                .name(name).attribute(PhaseAttribute.PHYSICAL).rare(rare)
                .weaponType(weaponType).job(job).squad(Squad.GROZA_SQUAD)
                .dollStat(buildDollStat())
                .build();
    }

    // ────────────────────────────── Test Data Setup ──────────────────────────────

    @BeforeEach
    void setUp() {
        dollRepository.save(buildDoll("Groza", DollRare.STANDARD, WeaponType.AR, Job.BULWARK));
        dollRepository.save(buildDoll("Nemesis", DollRare.STANDARD, WeaponType.RF, Job.SENTINEL));
        dollRepository.save(buildDoll("Vepley", DollRare.ELITE, WeaponType.SG, Job.VANGUARD));
        dollRepository.save(buildDoll("Colphen", DollRare.STANDARD, WeaponType.HG, Job.SUPPORT));
        dollRepository.save(buildDoll("AK-15", DollRare.ELITE, WeaponType.AR, Job.SENTINEL));
        dollRepository.save(buildDoll("AK-12", DollRare.ELITE, WeaponType.AR, Job.SUPPORT));
    }

    // ────────────────────────────── Search Tests ──────────────────────────────

    @Test
    @DisplayName("search: 이름 조건(name=Groza)으로 검색하면 해당 인형만 반환된다")
    void searchConditionName() {
        // given
        DollSearchCondition condition = new DollSearchCondition();
        condition.setName("Groza");

        // when
        Page<DollResponseDto> result = dollRepository.search(condition, PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Groza");
    }

    @Test
    @DisplayName("search: 레어도 조건(rare=STANDARD)으로 검색하면 해당 레어도의 인형만 반환된다")
    void searchConditionRare() {
        // given
        DollSearchCondition condition = new DollSearchCondition();
        condition.setRare(DollRare.STANDARD);

        // when
        Page<DollResponseDto> result = dollRepository.search(condition, PageRequest.of(0, 10));

        // then — STANDARD: Groza, Nemesis, Colphen
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent()).extracting(DollResponseDto::getName)
                .containsExactlyInAnyOrder("Groza", "Nemesis", "Colphen");
    }

    @Test
    @DisplayName("search: 이름 + 레어도 복합 조건으로 검색하면 교집합 결과만 반환된다")
    void searchConditionNameAndRare() {
        // given
        DollSearchCondition condition = new DollSearchCondition();
        condition.setName("AK");
        condition.setRare(DollRare.ELITE);

        // when
        Page<DollResponseDto> result = dollRepository.search(condition, PageRequest.of(0, 10));

        // then — "AK" 포함 + ELITE: AK-15, AK-12
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).extracting(DollResponseDto::getName)
                .containsExactlyInAnyOrder("AK-15", "AK-12");
    }

    @Test
    @DisplayName("search: 존재하지 않는 이름으로 검색하면 빈 페이지가 반환된다")
    void searchEmptyResult() {
        // given
        DollSearchCondition condition = new DollSearchCondition();
        condition.setName("Liva");

        // when
        Page<DollResponseDto> result = dollRepository.search(condition, PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();
    }

    @Test
    @DisplayName("search: 모든 조건이 null 이면 저장된 전체 인형이 반환된다")
    void searchAllConditionsNull() {
        // given — 조건 없음
        DollSearchCondition condition = new DollSearchCondition();

        // when
        Page<DollResponseDto> result = dollRepository.search(condition, PageRequest.of(0, 10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(6L);
    }
}
