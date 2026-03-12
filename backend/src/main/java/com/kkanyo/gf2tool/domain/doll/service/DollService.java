package com.kkanyo.gf2tool.domain.doll.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.entity.Doll;
import com.kkanyo.gf2tool.domain.doll.entity.DollStat;
import com.kkanyo.gf2tool.domain.doll.repository.DollRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 지연 로딩을 위해 기본적으로 트랜잭션 내에서 동작하도록 설정
public class DollService {

    private final DollRepository dollRepository;

    // List 대신 Page 타입을 반환하여 페이징 정보(전체 페이지 수 등)를 함께 전달
    public Page<DollResponseDto> findAll(@NonNull Pageable pageable) {
        return dollRepository.findAll(pageable)
                .map(DollResponseDto::fromEntity);
    }

    public DollStatResponseDto getDollStatByDollId(@NonNull Long id) {
        Doll doll = dollRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException(String.format("[ERROR] Not Exist Doll Stat [id:%d]", id)));

        DollStat dollStat = doll.getDollStat();
        if (dollStat == null) {
            // TODOerror handling
        }

        return DollStatResponseDto.fromEntity(dollStat);
    }

    @Transactional
    public DollSaveResponseDto save(DollSaveRequestDto dollRequestDto, DollStatSaveRequestDto dollStatRequestDto) {
        Doll doll = dollRequestDto.toEntity();
        doll.setDollStat(dollStatRequestDto.toEntity());

        // CascadeType.ALL 설정으로 인해 dollStat도 함께 저장
        Doll savedDoll = dollRepository.save(doll);

        return DollSaveResponseDto.fromEntity(savedDoll);
    }
}
