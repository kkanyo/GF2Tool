package com.kkanyo.gf2tool.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.repository.DollRepository;

@DataJpaTest
@ActiveProfiles("test")

public class DollRepositoryTest {

    @Autowired
    private DollRepository dollRepository;

    @Test
    @DisplayName("인형 저장 시 연관된 스탯도 함꼐 저장되어야 한다 (Cascasde 테스트)")
    void saveDollWithStat() {
        DollStat stat = DollStat.builder()
                .attack(100)
                .defense(50)
                .health(500)
                .stability(10)
                .criticalRate(20)
                .criticalDamage(120)
                .mobility(6)
                .weakness1(1)
                .weakness2(2)
                .build();

        Doll doll = Doll.builder()
                .name("Groza")
                .attribute1(1)
                .attribute2(2)
                .rare(5)
                .weaponType(1)
                .job(1)
                .dollStat(stat)
                .build();

        Doll savedDoll = dollRepository.save(doll);

        assertThat(savedDoll).isNotNull();
        assertThat(savedDoll.getDollStat().getAttack()).isEqualTo(100);
    }
}
