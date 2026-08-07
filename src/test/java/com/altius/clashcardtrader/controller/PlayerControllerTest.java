package com.altius.clashcardtrader.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.altius.clashcardtrader.advice.GlobalExceptionHandler;
import com.altius.clashcardtrader.config.SecurityConfiguration;
import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.service.PlayerService;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(PlayerController.class)
@Import({
        SecurityConfiguration.class,
        GlobalExceptionHandler.class
})
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PlayerService playerService;

    @Nested
    @DisplayName("PUT /v1/api/players/{playerId}/clan")
    class UpdateClan {

        @Test
        void shouldUpdatePlayersClanSuccessfully() throws Exception {
            UUID playerId = UUID.randomUUID();

            UpdateClanRequest request = new UpdateClanRequest(
                    "ABC123",
                    "Avengers");

            UpdateClanResponse response = new UpdateClanResponse(
                    playerId,
                    "DL",
                    "#QY2YOV",
                    "Pralaya",
                    Instant.now());

            when(playerService.updateClan(playerId, request))
                    .thenReturn(response);

            mockMvc.perform(
                    put("/v1/api/players/{playerId}/clan", playerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.playerId").value(playerId.toString()))
                    .andExpect(jsonPath("$.playerName").value("DL"))
                    .andExpect(jsonPath("$.clanTag").value("#QY2YOV"))
                    .andExpect(jsonPath("$.clanName").value("Pralaya"));

            verify(playerService)
                    .updateClan(playerId, request);
        }

    }
}
