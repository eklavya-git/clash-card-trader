package com.altius.clashcardtrader.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.altius.clashcardtrader.exception.InvalidClashTagException;

public class ClashTagTest {

    @Test
    @DisplayName("Should create ClashTag from a valid tag")
    void shouldCreateValidTag() {

        ClashTag tag = ClashTag.of("#P2L8Q");

        assertEquals("P2L8Q", tag.normalizedValue());
    }

    @Test
    @DisplayName("Should remove leading hash symbol")
    void shouldRemoveLeadingHash() {

        ClashTag tag = ClashTag.of(" #P2L");

        assertEquals("P2L", tag.normalizedValue());
    }

    @Test
    @DisplayName("Should convert tag to uppercase")
    void shouldConvertToUppercase() {

        ClashTag tag = ClashTag.of("#p2l8q");

        assertEquals("P2L8Q", tag.normalizedValue());
    }

    @Test
    @DisplayName("Should trim whitespace")
    void shouldTrimWhitespace() {

        ClashTag tag = ClashTag.of("   #P2L8Q   ");

        assertEquals("P2L8Q", tag.normalizedValue());
    }

    @Test
    @DisplayName("Should reject null tag")
    void shouldRejectNull() {

        assertThrows(
                InvalidClashTagException.class,
                () -> ClashTag.of(null));
    }

    @Test
    @DisplayName("Should reject blank tag")
    void shouldRejectBlank() {

        assertThrows(
                InvalidClashTagException.class,
                () -> ClashTag.of("   "));
    }

    @Test
    @DisplayName("Should reject invalid characters")
    void shouldRejectInvalidCharacters() {

        assertThrows(
                InvalidClashTagException.class,
                () -> ClashTag.of("#ABCDE"));
    }

    @Test
    @DisplayName("Should reject short tags")
    void shouldRejectShortTag() {

        assertThrows(
                InvalidClashTagException.class,
                () -> ClashTag.of("P2"));
    }

    @Test
    @DisplayName("Should reject long tags")
    void shouldRejectLongTag() {

        assertThrows(
                InvalidClashTagException.class,
                () -> ClashTag.of("P2L8QGRJCUVVV"));
    }

    @Test
    @DisplayName("Tags with different formatting should be equal")
    void shouldTreatEquivalentTagsAsEqual() {

        ClashTag first = ClashTag.of("#p2l8q");

        ClashTag second = ClashTag.of("P2L8Q");

        assertEquals(first, second);
    }

    @Test
    @DisplayName("Should return formatted display value")
    void shouldReturnDisplayValue() {

        ClashTag tag = ClashTag.of("P2L8Q");

        assertEquals("#P2L8Q", tag.displayValue());
    }

    @Test
    @DisplayName("toString should return display value")
    void shouldReturnDisplayValueFromToString() {

        ClashTag tag = ClashTag.of("P2L8Q");

        assertEquals("#P2L8Q", tag.toString());
    }

    @Test
    @DisplayName("Should normalize equivalent tags to the same hash code")
    void shouldGenerateSameHashCode() {

        ClashTag first = ClashTag.of("#P2L8Q");

        ClashTag second = ClashTag.of("p2l8q");

        assertEquals(first.hashCode(), second.hashCode());
    }
}
