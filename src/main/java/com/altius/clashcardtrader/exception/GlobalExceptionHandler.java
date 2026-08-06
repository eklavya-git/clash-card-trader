package com.altius.clashcardtrader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.altius.clashcardtrader.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handle(PlayerAlreadyRegisteredException ex) {
        return new ErrorResponse(
                "PLAYER_ALREADY_REGISTERED",
                ex.getMessage());
    }

    @ExceptionHandler(InvalidClashTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(InvalidClashTagException ex) {

        return new ErrorResponse(
                "INVALID_CLASH_TAG",
                ex.getMessage());
    }
}
