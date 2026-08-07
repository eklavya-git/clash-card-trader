package com.altius.clashcardtrader.entity;

import java.util.ArrayList;
import java.util.List;

import com.altius.clashcardtrader.domain.converter.ClashTagConverter;
import com.altius.clashcardtrader.domain.valueobject.ClashTag;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clans")
public class Clan extends BaseEntity {

    @Column(nullable = false, unique = false)
    private String name;

    @Convert(converter = ClashTagConverter.class)
    @Column(nullable = false, unique = true, length = 12)
    private ClashTag tag;

    @OneToMany(mappedBy = "clan")
    private final List<Player> players = new ArrayList<>();

    public static Clan create(ClashTag tag, String name) {
        Clan clan = new Clan();
        clan.setTag(tag);
        clan.setName(name);
        return clan;
    }
}
