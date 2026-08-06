package com.altius.clashcardtrader.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterPlayerRequest(

    @NotBlank
    String playerTag,

    @NotBlank
    String name
) {
}
