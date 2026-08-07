package com.altius.clashcardtrader.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.altius.clashcardtrader.advice.GlobalExceptionHandler;
import com.altius.clashcardtrader.config.SecurityConfiguration;
import com.altius.clashcardtrader.dto.response.ClanResponse;
import com.altius.clashcardtrader.exception.ClanNotFoundException;
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

        @Test
        @DisplayName("Should return clan details when clan exists")
        void shouldReturnClanWhenClanExists() throws Exception {

            ClanResponse response = new ClanResponse(
                    "#ABC123",
                    "Avengers",
                    5);

            when(clanService.getClan("ABC123"))
                    .thenReturn(response);

            mockMvc.perform(get("/v1/api/clans/ABC123"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.clanTag").value("#ABC123"))
                    .andExpect(jsonPath("$.clanName").value("Avengers"))
                    .andExpect(jsonPath("$.memberCount").value(5));

            verify(clanService).getClan("ABC123");
        }

        @Test
        @DisplayName("Should return 404 when clan does not exist")
        void shouldReturn404WhenClanDoesNotExist() throws Exception {
            String clanTag = "ABC123";
            String message = "Clan with tag '#ABC123' was not found.";

            when(clanService.getClan(clanTag))
                    .thenThrow(new ClanNotFoundException(message));

            mockMvc.perform(get("/v1/api/clans/{tag}", clanTag))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.errorCode").value("CLAN_NOT_FOUND"))
                    .andExpect(jsonPath("$.message").value(message))
                    .andExpect(jsonPath("$.path")
                            .value("/v1/api/clans/ABC123"));

            verify(clanService).getClan(clanTag);
        }

    }
}