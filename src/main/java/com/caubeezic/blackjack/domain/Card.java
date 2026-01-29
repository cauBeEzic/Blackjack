package com.caubeezic.blackjack.domain;

public class Card {
    private final int rank;
    private final Suit suit;

    public Card(int rank, Suit suit) {
        if (rank < 1 || rank > 13) {
            throw new IllegalArgumentException("Rank must be 1-13");
        }
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getBlackjackValue() {
        if (rank == 1) {
            return 1;
        }
        if (rank >= 11) {
            return 10;
        }
        return rank;
    }

    public String getImageName() {
        return rank + "_" + suit.getFileName() + ".png";
    }
}
