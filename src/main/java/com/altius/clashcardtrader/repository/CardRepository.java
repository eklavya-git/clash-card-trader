package com.altius.clashcardtrader.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altius.clashcardtrader.entity.Card;

@Repository
public interface  CardRepository extends JpaRepository<Card, UUID>{
    
}
