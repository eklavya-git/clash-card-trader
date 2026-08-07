package com.altius.clashcardtrader.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;
import com.altius.clashcardtrader.dto.response.ClanResponse;
import com.altius.clashcardtrader.entity.Clan;
import com.altius.clashcardtrader.exception.ClanNotFoundException;
import com.altius.clashcardtrader.repository.ClanRepository;
import com.altius.clashcardtrader.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClanService {

        private final ClanRepository clanRepository;
        private final PlayerRepository playerRepository;

        @Transactional(readOnly = true)
        public ClanResponse getClan(String tag) {
                ClashTag clanTag = ClashTag.of(tag);
                Clan clan = clanRepository.findByTag(clanTag)
                                .orElseThrow(() -> new ClanNotFoundException(tag));
                return new ClanResponse(
                                clan.getTag().toString(),
                                clan.getName(),
                                playerRepository.countByClan(clan));
        }
}
