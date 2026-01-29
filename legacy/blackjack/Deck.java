package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    //arrayList to hold the deck of Cards
    private ArrayList<Card> deck;

    //create an empty deck of cards
    public Deck() {
        deck = new ArrayList<>();
    }

    
    public Deck(Deck c) {
        deck = new ArrayList<>(c.getCards());
    }

    
    public Deck(boolean makeDeck) {
        deck = new ArrayList<>();
        if (makeDeck) {
            for (int i = 1; i <= 13; i++) {
                for (String suit : new String[]{"diamonds", "clubs", "hearts", "spades"}) {
                    String name = i + "_" + suit + ".png";
                    deck.add(new Card(name));
                }
            }
        }
    }

    //add card to the deck
    public void addCard(Card card) {
        deck.add(card);
    }

    //add multiple cards to the deck
    public void addCards(ArrayList<Card> cards) {
        deck.addAll(cards);
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random());
    }

    //take a card from deck
    public Card takeCard() {
        if (!deck.isEmpty()) {
            Card cardToTake = deck.remove(0);
            return cardToTake;
        } else {
            System.out.println("Deck is empty");
            return null;
        }
    }

    //check if the deck has cards left
    public boolean hasCards() {
        return !deck.isEmpty();
    }

    //return the number of cards left in deck
    public int cardsLeft() {
        return deck.size();
    }

    //get the arraylist containing all the cards in deck
     
    public ArrayList<Card> getCards() {
        return deck;
    }

    public void emptyDeck() {
        deck.clear();
    }

    //reset deck
    public void reset(Deck discard) {
        this.addCards(discard.getCards());
        this.shuffle();
        discard.emptyDeck();
        System.out.println("Ran out of cards, shuffling deck");
    }
}
