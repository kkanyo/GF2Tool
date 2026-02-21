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

import lombok.RequiredArgsConstructor;

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

    @GetMapping("/{id}")
    public ResponseEntity<DollStatResponseDto> getDollDetail(@PathVariable Long id) {

        return ResponseEntity.ok(dollService.getDollDetail(id));
    }

    @PostMapping
    public ResponseEntity<DollSaveResponseDto> createDoll(@RequestBody DollWithStatSaveRequestDto request) {

        DollSaveResponseDto response = dollService.save(request.getDoll(), request.getDollStat());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
