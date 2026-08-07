package com.altius.clashcardtrader.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;
import com.altius.clashcardtrader.entity.Clan;

public interface ClanRepository extends JpaRepository<Clan, UUID> {

    Optional<Clan> findByTag(ClashTag tag);

}
