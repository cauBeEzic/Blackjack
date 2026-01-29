package com.caubeezic.blackjack.web;

public class SessionStats {
    private int wins;
    private int losses;
    private int pushes;

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPushes() {
        return pushes;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public void incrementPushes() {
        pushes++;
    }
}
