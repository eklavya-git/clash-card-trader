package com.altius.clashcardtrader.mapper;

import com.altius.clashcardtrader.dto.response.CardResponse;
import com.altius.clashcardtrader.entity.Card;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface CardMapper {

    CardResponse toResponse(Card card);
    List<CardResponse> toResponse(List<Card> cards);
}