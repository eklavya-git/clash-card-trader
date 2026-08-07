package com.altius.clashcardtrader.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;
import com.altius.clashcardtrader.dto.request.UpdateClanRequest;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.entity.Clan;
import com.altius.clashcardtrader.entity.Player;
import com.altius.clashcardtrader.exception.PlayerNotFoundException;
import com.altius.clashcardtrader.mapper.PlayerMapper;
import com.altius.clashcardtrader.repository.ClanRepository;
import com.altius.clashcardtrader.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final ClanRepository clanRepository;
    private final PlayerMapper playerMapper;

    @Transactional
    public UpdateClanResponse updateClan(
            UUID playerId,
            UpdateClanRequest request) {

        // Find the player first to associate the clan name with
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId.toString()));

        ClashTag clanTag = ClashTag.of(request.clanTag());

        // Attempt to find the clan that the player wants to associate themself with. 
        // If not found, then create one and map the player to it 
        Clan clan = clanRepository.findByTag(clanTag)
                .orElseGet(() -> {
                    Clan newClan = Clan.create(
                            clanTag,
                            request.clanName());

                    return clanRepository.save(newClan);
                });

        player.setClan(clan);
        Player updatedPlayer = playerRepository.save(player);

        return playerMapper.toUpdateClanResponse(updatedPlayer);
    }
}