package com.kkanyo.gf2tool.global.error;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkanyo.gf2tool.domain.doll.exception.DollNotFoundException;

/**
 * GlobalExceptionHandler 단위 테스트
 * Stand-alone MockMvc으로 ControllerAdvice을 직접 등록하고,
 * Spring 컨텍스트 전체를 로드하지 않고 경량으로 검증한다.
 */
class GlobalExceptionHandlerTest {

    // ──────────────────────── Test Dummy Controller ────────────────────────

    @RestController
    static class DummyController {

        @GetMapping("/test/biz-error")
        public ResponseEntity<Void> throwBizError() {
            throw new DollNotFoundException();
        }

        @GetMapping("/test/internal-error")
        public ResponseEntity<Void> throwInternalError() {
            throw new RuntimeException("Unexpected error");
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
                        new GlobalExceptionHandler(factory),
                        new ValidationExceptionHandler(factory))
                .build();
    }

    // ────────────────────────────── Test ──────────────────────────────

    @Test
    @DisplayName("BusinessException 발생 시 404 + errorCode + path 가 응답에 포함된다")
    void handleBusinessException_404() throws Exception {
        mockMvc.perform(get("/test/biz-error"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("DOLL_NOT_FOUND"))
                .andExpect(jsonPath("$.path").value("/test/biz-error"));
    }

    @Test
    @DisplayName("처리되지 않은 Exception 발생 시 500 + errorCode=INTERNAL_SERVER_ERROR 로 응답한다")
    void handleInternalServerError_500() throws Exception {
        mockMvc.perform(get("/test/internal-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }
}
