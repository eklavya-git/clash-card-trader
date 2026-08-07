package com.altius.clashcardtrader.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.altius.clashcardtrader.advice.GlobalExceptionHandler;
import com.altius.clashcardtrader.config.SecurityConfiguration;
import com.altius.clashcardtrader.service.ClanService;

@WebMvcTest(ClanController.class)
@Import({
        SecurityConfiguration.class,
        GlobalExceptionHandler.class
})
class ClanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClanService clanService;

    @Nested
    @DisplayName("GET /v1/api/clans/{tag}")
    class GetClan {

    }
}