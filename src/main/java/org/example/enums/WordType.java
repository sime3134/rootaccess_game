package org.example.enums;

public enum WordType {
    CORRECT(0),
    SELECTABLE(1),
    FILLER(2),

    NONE(3);

    private final int wordType;
    WordType(int wordType) {
        this.wordType = wordType;
    }
}
