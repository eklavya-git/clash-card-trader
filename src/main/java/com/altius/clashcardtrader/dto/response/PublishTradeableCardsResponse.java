package com.altius.clashcardtrader.dto.response;

import java.time.Instant;

public record PublishTradeableCardsResponse(
        Instant publishedAt,
        int publishedCards
    ) {

}
