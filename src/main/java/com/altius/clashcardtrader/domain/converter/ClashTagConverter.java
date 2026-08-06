package com.altius.clashcardtrader.domain.converter;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClashTagConverter implements AttributeConverter<ClashTag, String>{

    @Override
    public String convertToDatabaseColumn(ClashTag attribute) {
        return attribute == null ? null : attribute.normalizedValue();
    }

    @Override
    public ClashTag convertToEntityAttribute(String dbData) {
        return dbData == null ? null : ClashTag.of(dbData);
    }
}
