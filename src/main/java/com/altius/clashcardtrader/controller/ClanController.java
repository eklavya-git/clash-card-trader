package com.altius.clashcardtrader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.altius.clashcardtrader.dto.response.ClanResponse;
import com.altius.clashcardtrader.service.ClanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/clans")
@RequiredArgsConstructor
public class ClanController {
    private final ClanService clanService;

    @GetMapping("/{tag}")
    public ClanResponse getClan(
            @PathVariable String tag) {

        return clanService.getClan(tag);
    }
}
