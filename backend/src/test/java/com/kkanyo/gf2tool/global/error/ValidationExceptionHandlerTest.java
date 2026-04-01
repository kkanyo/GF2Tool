package com.kkanyo.gf2tool.global.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.JsonPath;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * ValidationExceptionHandler 단위 테스트
 * Stand-alone MockMvc + Validator로 경량 검증을 수행한다.
 */
class ValidationExceptionHandlerTest {

    // ────────────────────────── Test Request Models ──────────────────────────

    record SingleFieldRequest(@NotBlank String name, @Min(1) int value) {
    }

    /** 6개의 @NotBlank 필드로 최대 5개 항목 제한을 검증하기 위한 요청 */
    record MultiFieldRequest(
            @NotBlank String field1,
            @NotBlank String field2,
            @NotBlank String field3,
            @NotBlank String field4,
            @NotBlank String field5,
            @NotBlank String field6) {
    }

    // ────────────────────────── Test Dummy Controller──────────────────────────

    @RestController
    static class DummyController {

        @PostMapping("/test/validate/single")
        public ResponseEntity<Void> validateSingle(@Valid @RequestBody SingleFieldRequest req) {
            return ResponseEntity.ok().build();
        }

        @PostMapping("/test/validate/multi")
        public ResponseEntity<Void> validateMulti(@Valid @RequestBody MultiFieldRequest req) {
            return ResponseEntity.ok().build();
        }
    }

    // ────────────────────────────── Setup ──────────────────────────────

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ErrorResponseFactory factory = new ErrorResponseFactory();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new DummyController())
                .setControllerAdvice(
                        new ValidationExceptionHandler(factory),
                        new GlobalExceptionHandler(factory))
                .build();
    }

    @Test
    @DisplayName("@Valid 가 실패하면 400 + errorCode=VALIDATION_ERROR + 실패한 필드명이 message 에 포함된다")
    void handleMethodArgumentNotValidSingle() throws Exception {
        // name 누락, value=0 (@Min(1) 위반)
        String body = """
                { "value": 0 }
                """;

        mockMvc.perform(post("/test/validate/single")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value(
                        org.hamcrest.Matchers.containsString("name")));
    }

    @Test
    @DisplayName("6개 필드에서 전부 오류가 발생해도 message 는 최대 5개 항목으로 잘린다")
    void handleMethodArgumentNotValidMulti() throws Exception {
        // 모든 @NotBlank 필드를 공백으로 보내서 6개 오류를 유발
        String body = "{}";

        mockMvc.perform(post("/test/validate/multi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(result -> {
                    String message = JsonPath.read(
                            result.getResponse().getContentAsString(), "$.message");
                    // "field: message" が ", " 区切りで最大 5 件以下であること
                    assertThat(message.split(", ")).hasSizeLessThanOrEqualTo(5);
                });
    }
}
