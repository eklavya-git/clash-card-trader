package com.altius.clashcardtrader.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TradeableCardRequest(
        @NotNull UUID cardId,
        @Positive int quantity) {

}
