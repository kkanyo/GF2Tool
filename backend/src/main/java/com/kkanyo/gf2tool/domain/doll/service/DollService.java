package com.kkanyo.gf2tool.domain.doll.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkanyo.gf2tool.domain.doll.dto.DollSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.repository.DollRepository;
import com.kkanyo.gf2tool.domain.doll.repository.DollStatRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DollService {

    private final DollRepository dollRepository;

    private final DollStatRepository dollStatRepository;

    public DollStatResponseDto getDollDetail(@NonNull Long id) {
        DollStat doll = dollStatRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("[ERROR] Not Exist Doll Stat [id:%d]", id)));

        return DollStatResponseDto.fromEntity(doll);
    }

    @Transactional
    public DollSaveResponseDto saveDoll(DollSaveRequestDto dollRequestDto, DollStatSaveRequestDto dollStatRequestDto) {
        Doll doll = dollRequestDto.toEntity();
        doll.setDollStat(dollStatRequestDto.toEntity());

        // CascadeType.ALL 설정으로 인해 dollStat도 함께 저장
        Doll savedDoll = dollRepository.save(doll);

        return DollSaveResponseDto.fromEntity(savedDoll);
    }
}
