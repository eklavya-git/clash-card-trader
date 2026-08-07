package com.altius.clashcardtrader;

import java.time.Instant;
import java.util.UUID;

import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.ClanResponse;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;

public class TestDataFactory {
    public static ClanResponse clanResponse() {

        return new ClanResponse(
                "#ABC123",
                "Avengers",
                5);
    }

    public static UUID playerId() {
        return UUID.randomUUID();
    }

    public static UpdateClanRequest updateClanRequest() {
        return new UpdateClanRequest(
                "#QY2YOV",
                "Pralaya");
    }

    public static UpdateClanResponse updateClanResponse(UUID playerId) {
        return new UpdateClanResponse(
                playerId,
                "DarkLord",
                "#QY2YOV",
                "Pralaya",
                Instant.now());
    }

    public static UpdateClanRequest badClanUpdateRequest() {
        return new UpdateClanRequest(
                "",
                "");
    }

    public static UpdateClanRequest invalidClanUpdateRequest() {
        return new UpdateClanRequest(
                "INVALID !!!",
                "Avengers");
    }

    private TestDataFactory() {
    }
}
