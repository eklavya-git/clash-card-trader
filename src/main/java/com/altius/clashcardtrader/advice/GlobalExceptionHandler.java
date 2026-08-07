package com.altius.clashcardtrader.advice;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.altius.clashcardtrader.dto.response.ErrorResponse;
import com.altius.clashcardtrader.exception.ClanNotFoundException;
import com.altius.clashcardtrader.exception.InvalidClashTagException;
import com.altius.clashcardtrader.exception.PlayerAlreadyRegisteredException;
import com.altius.clashcardtrader.exception.PlayerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ClanNotFoundException.class)
        public ResponseEntity<ErrorResponse> handle(ClanNotFoundException ex, HttpServletRequest request) {
                return buildErrorResponse(
                                HttpStatus.NOT_FOUND,
                                "CLAN_NOT_FOUND",
                                ex.getMessage(),
                                request);
        }

        @ExceptionHandler(PlayerNotFoundException.class)
        public ResponseEntity<ErrorResponse> handle(PlayerNotFoundException ex, HttpServletRequest request) {
                return buildErrorResponse(
                                HttpStatus.NOT_FOUND,
                                "PLAYER_NOT_FOUND",
                                ex.getMessage(),
                                request);
        }

        @ExceptionHandler(PlayerAlreadyRegisteredException.class)
        public ResponseEntity<ErrorResponse> handle(PlayerAlreadyRegisteredException ex, HttpServletRequest request) {
                return buildErrorResponse(
                                HttpStatus.CONFLICT,
                                "PLAYER_ALREADY_REGISTERED",
                                ex.getMessage(),
                                request);
        }

        @ExceptionHandler(InvalidClashTagException.class)
        public ResponseEntity<ErrorResponse> handle(InvalidClashTagException ex, HttpServletRequest request) {
                return buildErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                "INVALID_CLASH_TAG",
                                ex.getMessage(),
                                request);
        }

        private ResponseEntity<ErrorResponse> buildErrorResponse(
                        HttpStatus status,
                        String errorCode,
                        String message,
                        HttpServletRequest request) {

                return ResponseEntity
                                .status(status)
                                .body(ErrorResponse.builder()
                                                .timestamp(Instant.now())
                                                .status(status.value())
                                                .path(request.getRequestURI())
                                                .errorCode(errorCode)
                                                .message(message)
                                                .build());
        }
}
