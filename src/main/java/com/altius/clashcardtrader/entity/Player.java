package com.altius.clashcardtrader.entity;

import java.time.Instant;

import com.altius.clashcardtrader.domain.converter.ClashTagConverter;
import com.altius.clashcardtrader.domain.valueobject.ClashTag;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "players")
public class Player extends BaseEntity{
    
    @Column(nullable = false, unique = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id")
    private Clan clan;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @Convert(converter = ClashTagConverter.class)
    @Column(nullable = false, unique = true, length = 12)
    private ClashTag tag;

    public static Player register(ClashTag tag, String name) {
        Player player = new Player();
        player.setTag(tag);
        player.setName(name);
        return player;
    }
}
