package com.altius.clashcardtrader;

import com.altius.clashcardtrader.dto.response.ClanResponse;

public class TestDataFactory {
    public static ClanResponse clanResponse() {

        return new ClanResponse(
                "#ABC123",
                "Avengers",
                5
        );
    }
}
