package com.altius.clashcardtrader.entity.id;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PlayerTradeableCardId implements Serializable {

    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "card_id")
    private UUID cardId;
}
