package com.altius.clashcardtrader.entity;

import java.time.Instant;

import com.altius.clashcardtrader.entity.id.PlayerTradeableCardId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "player_tradeable_cards",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_player_card",
            columnNames = {
                "player_id",
                "card_id"
            }
        )
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerTradeableCard {

    @EmbeddedId
    private PlayerTradeableCardId id;
    
    @MapsId("playerId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @MapsId("cardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "published_at", nullable = false)
    private Instant publishedAt;
}
