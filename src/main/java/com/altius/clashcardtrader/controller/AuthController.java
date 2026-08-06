package com.altius.clashcardtrader.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altius.clashcardtrader.dto.request.RegisterPlayerRequest;
import com.altius.clashcardtrader.dto.response.RegisterPlayerResponse;
import com.altius.clashcardtrader.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterPlayerResponse register(
        @Valid @RequestBody RegisterPlayerRequest request
    ) {
        return authService.register(request);
    }
}
