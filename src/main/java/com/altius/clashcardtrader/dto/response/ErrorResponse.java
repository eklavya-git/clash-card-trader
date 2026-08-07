package com.altius.clashcardtrader.dto.response;

import java.time.Instant;

import lombok.Builder;

@Builder
public record ErrorResponse(
        Instant timestamp,
        int status,
        String path,
        String errorCode,
        String message) {

}
