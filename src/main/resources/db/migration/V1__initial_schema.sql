CREATE EXTENSION IF NOT EXISTS pgcrypto;

-------------------------------------------------------------------------------
-- CLANS
-------------------------------------------------------------------------------

CREATE TABLE clans
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tag         VARCHAR(20)  NOT NULL,
    name        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_clan_tag UNIQUE (tag)
);

COMMENT ON TABLE clans IS
'Clans imported or referenced by registered players.';

-------------------------------------------------------------------------------
-- PLAYERS
-------------------------------------------------------------------------------

CREATE TABLE players
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tag             VARCHAR(20)  NOT NULL,
    name            VARCHAR(100) NOT NULL,
    clan_id         UUID,
    last_login_at   TIMESTAMPTZ,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_player_tag UNIQUE (tag),

    CONSTRAINT fk_player_clan
        FOREIGN KEY (clan_id)
        REFERENCES clans (id)
);

COMMENT ON TABLE players IS
'Registered users of the Clash Card Trader application.';

-------------------------------------------------------------------------------
-- CARDS
-------------------------------------------------------------------------------

CREATE TABLE cards
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_card_name UNIQUE (name)
);

-------------------------------------------------------------------------------
-- PLAYER TRADEABLE CARDS
-------------------------------------------------------------------------------

CREATE TABLE player_tradeable_cards
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    player_id       UUID NOT NULL,
    card_id         UUID NOT NULL,
    quantity        INTEGER NOT NULL,
    published_at    TIMESTAMPTZ NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_player_tradeable_cards_quantity
        CHECK (quantity > 0),

    CONSTRAINT uk_player_card
        UNIQUE (player_id, card_id),

    CONSTRAINT fk_player_tradeable_cards_player
        FOREIGN KEY (player_id)
        REFERENCES players (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_player_tradeable_cards_card
        FOREIGN KEY (card_id)
        REFERENCES cards (id)
);

COMMENT ON TABLE player_tradeable_cards IS
'Latest published marketplace state representing cards available for trade by each player.';

-------------------------------------------------------------------------------
-- INDEXES
-------------------------------------------------------------------------------

CREATE INDEX idx_player_clan
    ON players (clan_id);

CREATE INDEX idx_tradeable_player
    ON player_tradeable_cards (player_id);

CREATE INDEX idx_tradeable_card
    ON player_tradeable_cards (card_id);

CREATE INDEX idx_tradeable_published_at
    ON player_tradeable_cards (published_at);