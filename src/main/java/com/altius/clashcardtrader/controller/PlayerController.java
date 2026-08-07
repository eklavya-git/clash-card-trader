package com.altius.clashcardtrader.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altius.clashcardtrader.dto.request.PublishTradeableCardsRequest;
import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.PublishTradeableCardsResponse;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.service.PlayerService;
import com.altius.clashcardtrader.service.TradeableCardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final TradeableCardService tradeableCardService;

    @PutMapping("/{playerId}/clan")
    public UpdateClanResponse updateClan(
            @PathVariable UUID playerId,
            @Valid @RequestBody UpdateClanRequest request) {

        return playerService.updateClan(playerId, request);
    }

    @PutMapping("/{playerId}/tradeable-cards")
    @ResponseStatus(HttpStatus.OK)
    public PublishTradeableCardsResponse publishTradeableCards(
            @PathVariable UUID playerId,
            @Valid @RequestBody PublishTradeableCardsRequest request) {

        return tradeableCardService.publish(playerId, request);
    }
}
