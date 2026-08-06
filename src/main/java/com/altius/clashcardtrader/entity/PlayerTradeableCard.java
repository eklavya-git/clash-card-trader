package com.altius.clashcardtrader.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "players_tradeable_cards",
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
public class PlayerTradeableCard extends BaseEntity{
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "published_at", nullable = false)
    private Instant publishedAt;
}
