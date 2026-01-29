package com.caubeezic.blackjack.domain;

public enum Suit {
    DIAMONDS("diamonds"),
    CLUBS("clubs"),
    HEARTS("hearts"),
    SPADES("spades");

    private final String fileName;

    Suit(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
