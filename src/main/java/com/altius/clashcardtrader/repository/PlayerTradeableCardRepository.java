package com.altius.clashcardtrader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altius.clashcardtrader.entity.Player;
import com.altius.clashcardtrader.entity.PlayerTradeableCard;
import com.altius.clashcardtrader.entity.id.PlayerTradeableCardId;

@Repository
public interface PlayerTradeableCardRepository extends JpaRepository<PlayerTradeableCard, PlayerTradeableCardId>{
    
    public List<PlayerTradeableCard> findByPlayer(Player player);
}
