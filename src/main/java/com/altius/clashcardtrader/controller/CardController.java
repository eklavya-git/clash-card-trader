package com.altius.clashcardtrader.controller;

import java.time.Duration;
import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.altius.clashcardtrader.dto.response.CardResponse;
import com.altius.clashcardtrader.service.CardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/v1/api/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardResponse>> getCardResponses() {
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Duration.ofHours(24)))
                .body(cardService.getAllCards());
    }
}
