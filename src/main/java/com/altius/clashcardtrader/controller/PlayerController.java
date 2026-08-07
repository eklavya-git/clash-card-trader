package com.altius.clashcardtrader.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.service.PlayerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PutMapping("/{playerId}/clan")
    public UpdateClanResponse updateClan(
            @PathVariable UUID playerId,
            @Valid @RequestBody UpdateClanRequest request) {

        return playerService.updateClan(playerId, request);
    }
}
