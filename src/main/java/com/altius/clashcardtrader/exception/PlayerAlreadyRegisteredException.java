package com.altius.clashcardtrader.exception;

import com.altius.clashcardtrader.domain.valueobject.ClashTag;

public class PlayerAlreadyRegisteredException extends RuntimeException{

    public PlayerAlreadyRegisteredException(ClashTag tag) {
        super("Player with tag: " + tag + "is already registered.");
    }

}
