package com.kkanyo.gf2tool.domain.doll.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.kkanyo.gf2tool.domain.doll.dto.DollResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollSaveResponseDto;
import com.kkanyo.gf2tool.domain.doll.dto.DollStatResponseDto;
import com.kkanyo.gf2tool.domain.doll.exception.DollNotFoundException;
import com.kkanyo.gf2tool.domain.doll.exception.DollStatNotFoundException;
import com.kkanyo.gf2tool.domain.doll.model.DollRare;
import com.kkanyo.gf2tool.domain.doll.model.Job;
import com.kkanyo.gf2tool.domain.doll.model.PhaseAttribute;
import com.kkanyo.gf2tool.domain.doll.service.DollService;
import com.kkanyo.gf2tool.domain.weapon.model.BulletType;
import com.kkanyo.gf2tool.domain.weapon.model.WeaponType;
import com.kkanyo.gf2tool.global.error.ErrorResponseFactory;

@WebMvcTest(DollController.class)
@Import(ErrorResponseFactory.class)
class DollControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private DollService dollService;

  // ────────────────────────────── Test helper ──────────────────────────────

  /** 필드 전부 유효한 인형 등록 JSON */
  private String validCreateRequestJson() {
    return """
        {
          "doll": {
            "name": "Groza",
            "attribute": "PHYSICAL",
            "rare": "STANDARD",
            "weaponType": "AR",
            "job": "BULWARK",
            "squad": "GROZA_SQUAD"
          },
          "dollStat": {
            "attack": 100,
            "defense": 50,
            "health": 500,
            "stability": 10,
            "criticalRate": 20,
            "criticalDamage": 120,
            "mobility": 6,
            "attackBonus": 10,
            "defenseBonus": 0,
            "healthBonus": 0,
            "weakness1": "HEAVY_AMMO",
            "weakness2": "BURN"
          }
        }
        """;
  }

  // ────────────────────────── GET /api/v1/dolls ──────────────────────────

  @Test
  @DisplayName("GET /api/v1/dolls: 200 OK — content 와 totalElements 가 반환된다")
  void getDollList() throws Exception {
    // given
    DollResponseDto dto = DollResponseDto.builder()
        .id(1L).name("Groza").attribute(PhaseAttribute.PHYSICAL).rare(DollRare.STANDARD)
        .weaponType(WeaponType.AR).job(Job.BULWARK)
        .build();
    given(dollService.findAll(any(Pageable.class)))
        .willReturn(new PageImpl<>(List.of(dto), PageRequest.of(0, 10), 1));

    // when / then
    mockMvc.perform(get("/api/v1/dolls"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content[0].name").value("Groza"))
        .andExpect(jsonPath("$.totalElements").value(1));
  }

  // ──────────────────────── GET /api/v1/dolls/{id}/stat ────────────────────────

  @Test
  @DisplayName("GET /api/v1/dolls/{id}/stat: 200 OK — 스탯 필드가 올바르게 반환된다")
  void getDollStat() throws Exception {
    // given
    DollStatResponseDto statDto = DollStatResponseDto.builder()
        .id(1L).attack(100).defense(50).health(500).stability(10)
        .criticalRate(20).criticalDamage(120).mobility(6)
        .attackBonus(10).defenseBonus(0).healthBonus(0)
        .weakness1(BulletType.HEAVY_AMMO).weakness2(PhaseAttribute.BURN)
        .build();
    given(dollService.getDollStatByDollId(1L)).willReturn(statDto);

    // when / then
    mockMvc.perform(get("/api/v1/dolls/1/stat"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.attack").value(100))
        .andExpect(jsonPath("$.health").value(500))
        .andExpect(jsonPath("$.weakness1").value("HEAVY_AMMO"));
  }

  @Test
  @DisplayName("GET /api/v1/dolls/{id}/stat: 인형이 없으면 404 + errorCode=DOLL_NOT_FOUND")
  void getDollStatDollNotFound() throws Exception {
    // given
    given(dollService.getDollStatByDollId(999L)).willThrow(new DollNotFoundException());

    // when / then
    mockMvc.perform(get("/api/v1/dolls/999/stat"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("DOLL_NOT_FOUND"));
  }

  @Test
  @DisplayName("GET /api/v1/dolls/{id}/stat: 스탯이 없으면 404 + errorCode=DOLL_STAT_NOT_FOUND")
  void getDollStatDollStatNotFound() throws Exception {
    // given
    given(dollService.getDollStatByDollId(1L)).willThrow(new DollStatNotFoundException());

    // when / then
    mockMvc.perform(get("/api/v1/dolls/1/stat"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode").value("DOLL_STAT_NOT_FOUND"));
  }

  // ────────────────────────── POST /api/v1/dolls──────────────────────────

  @Test
  @DisplayName("POST /api/v1/dolls: 유효한 요청이면 201 Created + Location 헤더가 반환된다")
  void createDoll() throws Exception {
    // given
    DollSaveResponseDto saveResponse = DollSaveResponseDto.builder()
        .id(1L).name("Groza").dollStatId(1L)
        .build();
    given(dollService.save(any(), any())).willReturn(saveResponse);

    // when / then
    mockMvc.perform(post("/api/v1/dolls")
        .contentType(MediaType.APPLICATION_JSON)
        .content(validCreateRequestJson()))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/v1/dolls/1"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Groza"));
  }

  @Test
  @DisplayName("POST /api/v1/dolls: name 누락 시 400 + errorCode=VALIDATION_ERROR")
  void createDollValidationError() throws Exception {
    // given — name 필드를 제거한 바디
    String invalidBody = """
        {
          "doll": {
            "attribute": "BURN",
            "rare": "ELITE",
            "weaponType": "AR",
            "job": "SENTINEL",
            "squad": "GROZA_SQUAD"
          },
          "dollStat": {
            "attack": 100,
            "defense": 50,
            "health": 500,
            "stability": 10,
            "criticalRate": 20,
            "criticalDamage": 120,
            "mobility": 6,
            "attackBonus": 10,
            "defenseBonus": 0,
            "healthBonus": 0,
            "weakness1": "LIGHT_AMMO",
            "weakness2": "BURN"
          }
        }
        """;

    // when / then
    mockMvc.perform(post("/api/v1/dolls")
        .contentType(MediaType.APPLICATION_JSON)
        .content(invalidBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
        .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("name")));
  }
}
