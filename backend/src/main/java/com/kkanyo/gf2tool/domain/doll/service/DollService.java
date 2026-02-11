package com.kkanyo.gf2tool.domain.doll.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.repository.DollStatRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DollService {

    private final DollStatRepository dollStatRepository;

    public DollStatResponseDto getDollDetail(@NonNull Long id) {
        DollStat doll = dollStatRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("[ERROR] Not Exist Doll Stat [id:%d]", id)));

        return new DollStatResponseDto(doll);
    }
}
