package com.altius.clashcardtrader.dto.response;

public record ErrorResponse(
    String errorCode,
    String message
) {
    
}
