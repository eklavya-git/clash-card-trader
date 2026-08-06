package com.altius.clashcardtrader.service;

import org.springframework.stereotype.Service;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;
import com.altius.clashcardtrader.dto.request.RegisterPlayerRequest;
import com.altius.clashcardtrader.dto.response.RegisterPlayerResponse;
import com.altius.clashcardtrader.entity.Player;
import com.altius.clashcardtrader.exception.PlayerAlreadyRegisteredException;
import com.altius.clashcardtrader.mapper.PlayerMapper;
import com.altius.clashcardtrader.repository.PlayerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public RegisterPlayerResponse register(RegisterPlayerRequest request) {
        ClashTag playerTag = ClashTag.of(request.playerTag());
        playerRepository.findByTag(playerTag)
                .ifPresent(player -> {
                    throw new PlayerAlreadyRegisteredException(playerTag);
                });

        return createAndSavePlayer(request);
    }

    private RegisterPlayerResponse createAndSavePlayer(RegisterPlayerRequest request) {
        Player player = Player
                .register(ClashTag.of(request.playerTag()), request.name());

        Player savedPlayer = playerRepository.save(player);
        System.out.println("Saved player");
        RegisterPlayerResponse response = playerMapper
        .toRegisterPlayerResponse(savedPlayer);

        System.out.println("Mapped response");

        return response;
    }
}
