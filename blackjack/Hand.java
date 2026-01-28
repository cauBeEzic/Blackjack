package blackjack;

import java.util.ArrayList;


public class Hand {

    private ArrayList<Card> hand;
    public Hand(){
        hand = new ArrayList<Card>();
    }

    //take card from deck, to add to hand (draw a card)
    public void takeCardFromDeck(Deck deck){
    		hand.add(deck.takeCard());
    	
        
    }

    //reset hand
    public void discardHandToDeck(Deck discardDeck){
        //copy cards from hand to discardDeck
        discardDeck.addCards(hand);

        //clear the hand
        hand.clear();
    }

    //print the hand 
    // 
    public String toString(){
        String output = "";
        for(Card card: hand){
            output += card + " - ";
        }
        return output;
    }


    //calculate points
    public int calculatedValue() {
        int value = 0;
        int aceCount = 0;
        for (Card card : hand) {
            value += card.getPts();
            if (card.getPts() == 1) {
                aceCount++;
            }
        }
        while (aceCount > 0 && value + 10 <= 21) {
            value += 10;
            aceCount--;
        }
        return value;
    }

    //return the card we got
    public Card getCard(int index){
        return hand.get(index);
    }

    //get the number of cards in this hand
    public int getHandSize(){
        return hand.size();
    }

}
