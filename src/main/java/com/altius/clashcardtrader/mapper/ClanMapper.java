package com.altius.clashcardtrader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.altius.clashcardtrader.dto.response.ClanResponse;
import com.altius.clashcardtrader.entity.Clan;

@Mapper(
    config = MapperConfiguration.class,
    uses = ClashTagMapper.class
)
public interface ClanMapper {

    @Mapping(target = "clanTag", source = "tag")
    @Mapping(target = "clanName", source = "name")
    @Mapping(
        target = "memberCount",
        expression = "java(clan.getPlayers().size())"
    )
    ClanResponse toResponse(Clan clan);

}
