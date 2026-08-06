package com.altius.clashcardtrader.mapper;

import org.springframework.stereotype.Component;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;

@Component
public class ClashTagMapper {
    
    public String map(ClashTag clashTag) {
        return clashTag == null ? null : clashTag.toString();
    }

    public ClashTag map(String value) {
        return value == null ? null : ClashTag.of(value);
    }
}
