package com.altius.clashcardtrader.domain.valueobject;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import com.altius.clashcardtrader.exception.InvalidClashTagException;

public class ClashTag implements Serializable {

    /*
     * Clash of Clans uses a Base-14 alphabet.
     *
     * Valid characters:
     * 0 2 8 9 P Y L Q G R J C U V
     */
    private static final Pattern VALID_TAG_PATTERN = Pattern.compile("^[0289PYLQGRJCUV]{3,12}$");

    private final String value;

    private ClashTag(String value) {
        this.value = normalizeAndValidate(value);
    }

    private static String normalizeAndValidate(String tag) {

        if (tag == null || tag.isBlank()) {
            throw new InvalidClashTagException("Clash tag cannot be null or blank.");
        }

        String normalized = tag
                .trim()
                .toUpperCase(Locale.ROOT);

        if (normalized.length() < 3) {
            throw new InvalidClashTagException(
                    "Clash tag must contain at least 3 characters.");
        }

        if (normalized.length() > 12) {
            throw new InvalidClashTagException(
                    "Clash tag cannot exceed 12 characters.");
        }

        if (normalized.startsWith("#")) {
            normalized = normalized.substring(1);
        }

        if (!VALID_TAG_PATTERN.matcher(normalized).matches()) {
            throw new InvalidClashTagException(
                    "Invalid Clash tag: " + tag +
                            ". Allowed characters are: 0289PYLQGRJCUV.");
        }
        return normalized;
    }

    public String normalizedValue() {
        return value;
    }

    public static ClashTag of(String value) {
        return new ClashTag(value);
    }

    @Override
    public String toString() {
        return "#" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ClashTag clashTag))
            return false;
        return value.equals(clashTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String displayValue() {
        return "#" + value;
    }
}