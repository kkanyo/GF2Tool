package com.kkanyo.gf2tool.domain.doll.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import com.kkanyo.gf2tool.global.config.QuerydslConfig;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.model.Squad;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
class DollRepositoryTest {

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

    private static Doll buildDoll(String name, DollRare rare) {
        return Doll.builder()
                .name(name).attribute(PhaseAttribute.PHYSICAL).rare(rare).weaponType(WeaponType.AR).job(Job.BULWARK)
                .squad(Squad.GROZA_SQUAD)
                .dollStat(buildDollStat())
                .build();
    }

    // ─────────────────────── Cascade (1:1 저장) ───────────────────────

    @Test
    @DisplayName("save: 인형 저장 시 연관된 스탯도 함께 저장된다 (CascadeType.ALL)")
    void save() {
        // given
        Doll doll = buildDoll("Groza", DollRare.ELITE);

        // when
        Doll savedDoll = dollRepository.save(doll);

        // then
        assertThat(savedDoll.getId()).isNotNull();
        assertThat(savedDoll.getDollStat()).isNotNull();
        assertThat(savedDoll.getDollStat().getId()).isNotNull();
        assertThat(savedDoll.getDollStat().getAttack()).isEqualTo(100);
    }

    // ────────────────────────────── findAll (페이징) ──────────────────────────────

    @Test
    @DisplayName("findAll: 페이지 크기 2 로 조회 시 content 크기와 totalElements 가 정확하다")
    void findAll() {
        // given — 3개 저장
        dollRepository.save(buildDoll("Groza", DollRare.STANDARD));
        dollRepository.save(buildDoll("Vepley", DollRare.ELITE));
        dollRepository.save(buildDoll("Colphen", DollRare.STANDARD));

        // when
        Page<Doll> page = dollRepository.findAll(PageRequest.of(0, 2, Sort.by("id")));

        // then
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(3L);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    // ────────────────────────────── findById ──────────────────────────────

    @Test
    @DisplayName("findById: 존재하지 않는 ID 조회 시 Optional.empty() 가 반환된다")
    void findByIdEmpty() {
        // when
        Optional<Doll> result = dollRepository.findById(Long.MAX_VALUE);

        // then
        assertThat(result).isEmpty();
    }
}
