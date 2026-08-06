package com.altius.clashcardtrader.dto.response;

import java.time.Instant;
import java.util.UUID;

public record RegisterPlayerResponse(
    UUID playerId,
    String playerTag,
    String name,
    Instant registeredAt
) {
    
}
