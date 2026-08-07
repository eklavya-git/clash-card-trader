package com.altius.clashcardtrader.dto.response;

import java.time.Instant;
import java.util.UUID;

public record UpdateClanResponse(
        UUID playerId,
        String playerName,
        String clanTag,
        String clanName,
        Instant updatedAt
) {
}