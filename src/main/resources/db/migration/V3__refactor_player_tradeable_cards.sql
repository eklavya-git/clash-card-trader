DROP TABLE IF EXISTS player_tradeable_cards;

CREATE TABLE player_tradeable_cards
(
    player_id       UUID NOT NULL,
    card_id         UUID NOT NULL,
    quantity        INTEGER NOT NULL,
    published_at    TIMESTAMPTZ NOT NULL,

    CONSTRAINT pk_player_tradeable_cards
        PRIMARY KEY (player_id, card_id),

    CONSTRAINT chk_player_tradeable_cards_quantity
        CHECK (quantity > 0),

    CONSTRAINT fk_player_tradeable_cards_player
        FOREIGN KEY (player_id)
        REFERENCES players(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_player_tradeable_cards_card
        FOREIGN KEY (card_id)
        REFERENCES cards(id)
);