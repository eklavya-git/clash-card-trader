package com.altius.clashcardtrader.dto.response;

public record ClanResponse(
    String clanTag,
    String clanName,
    long memberCount
) {
    
}
