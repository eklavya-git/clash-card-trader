package com.altius.clashcardtrader.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record PublishTradeableCardsRequest(
        @NotEmpty List<TradeableCardRequest> cards) {

}
