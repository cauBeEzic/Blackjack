package com.caubeezic.blackjack.domain;

public class GameState {
    private Deck deck;
    private java.util.List<Hand> playerHands;
    private java.util.List<GameOutcome> handOutcomes;
    private java.util.List<Boolean> handDone;
    private int currentHandIndex;
    private Hand dealerHand;
    private GameOutcome outcome;
    private boolean inProgress;
    private boolean dealerRevealed;
    private boolean resultsApplied;

    public GameState() {
        startNewGame();
    }

    public void startNewGame() {
        this.deck = new Deck();
        this.playerHands = new java.util.ArrayList<>();
        this.handOutcomes = new java.util.ArrayList<>();
        this.handDone = new java.util.ArrayList<>();
        this.currentHandIndex = 0;
        Hand first = new Hand();
        this.playerHands.add(first);
        this.handOutcomes.add(null);
        this.handDone.add(false);
        this.dealerHand = new Hand();
        this.outcome = GameOutcome.PLAYING;
        this.inProgress = true;
        this.dealerRevealed = false;
        this.resultsApplied = false;
        dealInitialCards();
        checkInitialBlackjack();
    }

    private void dealInitialCards() {
        currentHand().addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        currentHand().addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    private void checkInitialBlackjack() {
        boolean playerBJ = currentHand().getValue() == 21 && currentHand().size() == 2;
        boolean dealerBJ = dealerHand.getValue() == 21 && dealerHand.size() == 2;
        if (playerBJ || dealerBJ) {
            dealerRevealed = true;
            inProgress = false;
            if (playerBJ && dealerBJ) {
                outcome = GameOutcome.PUSH;
                handOutcomes.set(0, GameOutcome.PUSH);
            } else if (playerBJ) {
                outcome = GameOutcome.BLACKJACK;
                handOutcomes.set(0, GameOutcome.BLACKJACK);
            } else {
                outcome = GameOutcome.LOSE;
                handOutcomes.set(0, GameOutcome.LOSE);
            }
        }
    }

    public void playerHit() {
        if (!inProgress) {
            return;
        }
        Hand hand = currentHand();
        hand.addCard(deck.draw());
        int value = hand.getValue();
        if (value > 21) {
            handOutcomes.set(currentHandIndex, GameOutcome.BUST);
            handDone.set(currentHandIndex, true);
            advanceToNextHandOrDealer();
        } else if (value == 21) {
            handDone.set(currentHandIndex, true);
            advanceToNextHandOrDealer();
        } else if (hand.size() >= 5) {
            handOutcomes.set(currentHandIndex, GameOutcome.WIN);
            handDone.set(currentHandIndex, true);
            advanceToNextHandOrDealer();
        }
    }

    public void playerStand() {
        if (!inProgress) {
            return;
        }
        handDone.set(currentHandIndex, true);
        advanceToNextHandOrDealer();
    }

    public void playerSplit() {
        if (!inProgress || !canSplitCurrentHand()) {
            return;
        }
        Hand current = currentHand();
        Card first = current.getCards().get(0);
        Card second = current.getCards().get(1);
        Hand handA = new Hand();
        Hand handB = new Hand();
        handA.addCard(first);
        handB.addCard(second);
        handA.addCard(deck.draw());
        handB.addCard(deck.draw());
        playerHands.set(currentHandIndex, handA);
        playerHands.add(currentHandIndex + 1, handB);
        handOutcomes.set(currentHandIndex, null);
        handOutcomes.add(currentHandIndex + 1, null);
        handDone.set(currentHandIndex, false);
        handDone.add(currentHandIndex + 1, false);
    }

    private void advanceToNextHandOrDealer() {
        int nextIndex = findNextPlayableHand(currentHandIndex + 1);
        if (nextIndex != -1) {
            currentHandIndex = nextIndex;
            return;
        }
        dealerRevealed = true;
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.draw());
        }
        settleHands();
        endRound();
    }

    private int findNextPlayableHand(int start) {
        for (int i = start; i < handDone.size(); i++) {
            if (!handDone.get(i)) {
                return i;
            }
        }
        return -1;
    }

    private void settleHands() {
        int dealerValue = dealerHand.getValue();
        for (int i = 0; i < playerHands.size(); i++) {
            if (handOutcomes.get(i) != null) {
                continue;
            }
            int playerValue = playerHands.get(i).getValue();
            if (dealerValue > 21) {
                handOutcomes.set(i, GameOutcome.WIN);
            } else if (playerValue > dealerValue) {
                handOutcomes.set(i, GameOutcome.WIN);
            } else if (playerValue < dealerValue) {
                handOutcomes.set(i, GameOutcome.LOSE);
            } else {
                handOutcomes.set(i, GameOutcome.PUSH);
            }
        }
    }

    private void endRound() {
        inProgress = false;
        dealerRevealed = true;
    }

    public String getStatusMessage() {
        if (inProgress) {
            return "Hit, Stand, or Split?";
        }
        boolean anyBlackjack = handOutcomes.stream().anyMatch(o -> o == GameOutcome.BLACKJACK);
        if (anyBlackjack) {
            return "Blackjack!";
        }
        return "Round complete.";
    }

    public Deck getDeck() {
        return deck;
    }

    public java.util.List<Hand> getPlayerHands() {
        return java.util.Collections.unmodifiableList(playerHands);
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public GameOutcome getOutcome() {
        return outcome;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public boolean isDealerRevealed() {
        return dealerRevealed;
    }

    public int getCurrentHandIndex() {
        return currentHandIndex;
    }

    public java.util.List<GameOutcome> getHandOutcomes() {
        return java.util.Collections.unmodifiableList(handOutcomes);
    }

    public boolean canSplitCurrentHand() {
        if (!inProgress) {
            return false;
        }
        Hand hand = currentHand();
        if (hand.size() != 2) {
            return false;
        }
        Card card0 = hand.getCards().get(0);
        Card card1 = hand.getCards().get(1);
        int rank0 = card0.getRank();
        int rank1 = card1.getRank();
        if (rank0 == rank1) {
            return true;
        }
        return card0.getBlackjackValue() == 10 && card1.getBlackjackValue() == 10;
    }

    public boolean isRoundComplete() {
        return !inProgress;
    }

    public boolean isResultsApplied() {
        return resultsApplied;
    }

    public void markResultsApplied() {
        this.resultsApplied = true;
    }

    private Hand currentHand() {
        return playerHands.get(currentHandIndex);
    }
}
