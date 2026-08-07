package com.altius.clashcardtrader.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateClanRequest(

                @NotBlank String clanTag,
                @NotBlank String clanName) {

}
