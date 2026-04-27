package com.kkanyo.gf2tool.domain.doll.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSearchCondition;

public interface DollRepositoryCustom {
    Page<DollResponseDto> search(DollSearchCondition condition, Pageable pageable);
}
