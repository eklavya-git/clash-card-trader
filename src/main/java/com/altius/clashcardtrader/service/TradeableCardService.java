package com.altius.clashcardtrader.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altius.clashcardtrader.dto.request.PublishTradeableCardsRequest;
import com.altius.clashcardtrader.dto.request.TradeableCardRequest;
import com.altius.clashcardtrader.dto.response.PublishTradeableCardsResponse;
import com.altius.clashcardtrader.entity.Card;
import com.altius.clashcardtrader.entity.Player;
import com.altius.clashcardtrader.entity.PlayerTradeableCard;
import com.altius.clashcardtrader.exception.CardNotFoundException;
import com.altius.clashcardtrader.exception.PlayerNotFoundException;
import com.altius.clashcardtrader.repository.CardRepository;
import com.altius.clashcardtrader.repository.PlayerRepository;
import com.altius.clashcardtrader.repository.PlayerTradeableCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeableCardService {
    private final PlayerRepository playerRepository;
    private final CardRepository cardRepository;
    private final PlayerTradeableCardRepository playerTradeableCardRepository;

    public PublishTradeableCardsResponse publish(
            UUID playerId,
            PublishTradeableCardsRequest request) {

        Player player = findPlayer(playerId);
        Instant publishedAt = Instant.now();

        Map<UUID, Integer> requestedCards = normalize(request);

        Map<UUID, Card> cards = loadCards(requestedCards.keySet());

        Map<UUID, PlayerTradeableCard> publishedCards = loadPublishedCards(player);

        synchronize(
                player,
                requestedCards,
                cards,
                publishedCards,
                publishedAt);

        return new PublishTradeableCardsResponse(
                publishedAt,
                requestedCards.size());
    }

    private Player findPlayer(UUID playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(
                        "Player with id '" + playerId + "' was not found."));
    }

    private Map<UUID, Integer> normalize(
            PublishTradeableCardsRequest request) {
        return request.cards()
                .stream()
                .filter(card -> card.quantity() > 1)
                .collect(Collectors.toMap(
                        TradeableCardRequest::cardId,
                        TradeableCardRequest::quantity,
                        (oldValue, newValue) -> newValue,
                        HashMap::new));
    }

    private Map<UUID, Card> loadCards(Set<UUID> cardIds) {
        List<Card> cards = cardRepository.findAllById(cardIds);

        if (cards.size() != cardIds.size()) {
            Set<UUID> foundIds = cards.stream()
                    .map(Card::getId)
                    .collect(Collectors.toSet());

            Set<UUID> missingCardIds = new HashSet<>(cardIds);
            missingCardIds.removeAll(foundIds);

            if (!missingCardIds.isEmpty()) {
                throw new CardNotFoundException(
                        "Cards not found: " + missingCardIds);
            }
        }

        return cards.stream()
                .collect(Collectors.toMap(
                        Card::getId,
                        Function.identity()));
    }

    private Map<UUID, PlayerTradeableCard> loadPublishedCards(
            Player player) {

        return playerTradeableCardRepository
                .findByPlayer(player)
                .stream()
                .collect(Collectors.toMap(
                        card -> card.getCard().getId(),
                        Function.identity()));
    }

    private void synchronize(
            Player player,
            Map<UUID, Integer> requestedCards,
            Map<UUID, Card> cards,
            Map<UUID, PlayerTradeableCard> publishedCards,
            Instant publishedAt) {

        synchronizeExistingCards(
                requestedCards,
                publishedCards,
                publishedAt);

        publishNewCards(
                player,
                requestedCards,
                publishedCards,
                publishedAt);

        removeUnpublishedCards(
                requestedCards,
                publishedCards);
    }

    private void synchronizeExistingCards(
            Map<UUID, Integer> requestedCards,
            Map<UUID, PlayerTradeableCard> publishedCards,
            Instant publishedAt) {

        requestedCards.forEach((cardId, quantity) -> {

            PlayerTradeableCard publishedCard = publishedCards.get(cardId);

            if (publishedCard != null) {
                publishedCard.setQuantity(quantity);
                publishedCard.setPublishedAt(publishedAt);
            }
        });
    }

    private void publishNewCards(
            Player player,
            Map<UUID, Integer> requestedCards,
            Map<UUID, PlayerTradeableCard> publishedCards,
            Instant publishedAt) {

        List<PlayerTradeableCard> newCards = new ArrayList<>();

        for (Map.Entry<UUID, Integer> entry : requestedCards.entrySet()) {

            if (publishedCards.containsKey(entry.getKey())) {
                continue;
            }

            Card card = cardRepository.findById(entry.getKey())
                    .orElseThrow(() -> new CardNotFoundException(
                            "Card with id '" + entry.getKey() + "' was not found."));

            newCards.add(
                    PlayerTradeableCard.builder()
                            .player(player)
                            .card(card)
                            .quantity(entry.getValue())
                            .publishedAt(publishedAt)
                            .build());
        }
        playerTradeableCardRepository.saveAll(newCards);
    }

    private void removeUnpublishedCards(
            Map<UUID, Integer> requestedCards,
            Map<UUID, PlayerTradeableCard> publishedCards) {

        List<PlayerTradeableCard> cardsToDelete = publishedCards.values()
                .stream()
                .filter(card -> !requestedCards.containsKey(
                        card.getCard().getId()))
                .toList();

        playerTradeableCardRepository.deleteAll(cardsToDelete);
    }

}
