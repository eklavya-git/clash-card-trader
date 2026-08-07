package com.altius.clashcardtrader.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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

import com.altius.clashcardtrader.TestDataFactory;
import com.altius.clashcardtrader.advice.GlobalExceptionHandler;
import com.altius.clashcardtrader.config.SecurityConfiguration;
import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.exception.InvalidClashTagException;
import com.altius.clashcardtrader.exception.PlayerNotFoundException;
import com.altius.clashcardtrader.service.PlayerService;
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

        UUID playerId = TestDataFactory.playerId();
        UpdateClanRequest request = TestDataFactory.updateClanRequest();
        UpdateClanResponse response = TestDataFactory.updateClanResponse(playerId);

        @Test
        void shouldUpdatePlayersClanSuccessfully() throws Exception {

            when(playerService.updateClan(playerId, request))
                    .thenReturn(response);

            mockMvc.perform(
                    put("/v1/api/players/{playerId}/clan", playerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.playerId").value(playerId.toString()))
                    .andExpect(jsonPath("$.playerName").value("DarkLord"))
                    .andExpect(jsonPath("$.clanTag").value("#QY2YOV"))
                    .andExpect(jsonPath("$.clanName").value("Pralaya"));

            verify(playerService)
                    .updateClan(playerId, request);
        }

        @Test
        @DisplayName("Should return 404 when player does not exist")
        void shouldReturn404WhenPlayerDoesNotExist() throws Exception {
            
            String message = "Player with id '" + playerId + "' was not found.";

            when(playerService.updateClan(playerId, request))
                    .thenThrow(new PlayerNotFoundException(message));

            mockMvc.perform(
                    put("/v1/api/players/{playerId}/clan", playerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.errorCode").value("PLAYER_NOT_FOUND"))
                    .andExpect(jsonPath("$.message").value(message))
                    .andExpect(jsonPath("$.path")
                            .value("/v1/api/players/" + playerId + "/clan"));

            verify(playerService).updateClan(playerId, request);
        }

        @Test
        @DisplayName("Should return 400 when request validation fails")
        void shouldReturn400WhenRequestValidationFails() throws Exception {

            UpdateClanRequest request = TestDataFactory.badClanUpdateRequest();

            mockMvc.perform(
                    put("/v1/api/players/{playerId}/clan", playerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(playerService);
        }

        @Test
        @DisplayName("Should return 400 when clan tag is invalid")
        void shouldReturn400WhenClanTagIsInvalid() throws Exception {

            UpdateClanRequest request = TestDataFactory.invalidClanUpdateRequest();

            String message = "Invalid Clash tag: INVALID!!!";

            when(playerService.updateClan(playerId, request))
                    .thenThrow(new InvalidClashTagException(message));

            mockMvc.perform(
                    put("/v1/api/players/{playerId}/clan", playerId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.errorCode").value("INVALID_CLASH_TAG"))
                    .andExpect(jsonPath("$.message").value(message));

            verify(playerService).updateClan(playerId, request);
        }
    }
}
