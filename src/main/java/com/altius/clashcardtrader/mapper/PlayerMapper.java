package com.altius.clashcardtrader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.altius.clashcardtrader.dto.response.RegisterPlayerResponse;
import com.altius.clashcardtrader.dto.response.UpdateClanResponse;
import com.altius.clashcardtrader.entity.Player;

@Mapper(
    config = MapperConfiguration.class, 
    uses = ClashTagMapper.class
)
public interface PlayerMapper {

    @Mapping(target = "playerTag", expression = "java(player.getTag().toString())")
    @Mapping(target = "registeredAt", source = "createdAt")
    @Mapping(target = "playerId", source = "id")
    RegisterPlayerResponse toRegisterPlayerResponse(Player player);

    @Mapping(target = "playerId", source = "id")
    @Mapping(target = "playerName", source = "name")
    @Mapping(target = "clanTag", source = "clan.tag")
    @Mapping(target = "clanName", source = "clan.name")
    @Mapping(target = "updatedAt", source = "updatedAt")
    UpdateClanResponse toUpdateClanResponse(Player player);
}
