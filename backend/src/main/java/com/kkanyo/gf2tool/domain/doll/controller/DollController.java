package com.kkanyo.gf2tool.domain.doll.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollWithStatSaveRequestDto;
import com.kkanyo.gf2tool.domain.doll.service.DollService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Doll", description = "인형 관련 API")
@RestController
@RequestMapping("/api/v1/dolls")
@RequiredArgsConstructor
public class DollController {

    private final DollService dollService;

    @GetMapping
    public ResponseEntity<Page<DollResponseDto>> getDollList(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(dollService.findAll(pageable));
    }

    @GetMapping("/{id}/stat")
    public ResponseEntity<DollStatResponseDto> getDollStat(@PathVariable Long id) {

        return ResponseEntity.ok(dollService.getDollStatByDollId(id));
    }

    @Operation(summary = "인형 등록", description = "새로운 인형 정보를 DB에 저장합니다.")
    @PostMapping
    public ResponseEntity<DollSaveResponseDto> createDoll(@Valid @RequestBody DollWithStatSaveRequestDto request) {

        DollSaveResponseDto response = dollService.save(request.getDoll(), request.getDollStat());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
