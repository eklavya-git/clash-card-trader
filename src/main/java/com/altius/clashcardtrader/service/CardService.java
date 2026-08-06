package com.altius.clashcardtrader.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.altius.clashcardtrader.dto.response.CardResponse;
import com.altius.clashcardtrader.repository.CardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
    
    private final CardRepository cardRepository;

    public List<CardResponse> getAllCards() {
        return cardRepository.findAll(Sort.by("name"))
        .stream()
        .map(card -> new CardResponse(
            card.getId(),
            card.getName()
        ))
        .toList();
    }
}
