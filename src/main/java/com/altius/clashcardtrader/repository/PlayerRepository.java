package com.altius.clashcardtrader.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;
import com.altius.clashcardtrader.entity.Clan;
import com.altius.clashcardtrader.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findByTag(ClashTag tag);
    List<Player> findByNameIgnoreCase(String name);
    long countByClan(Clan clan);
}
