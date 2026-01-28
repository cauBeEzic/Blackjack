package blackjack;

import javax.swing.*;
import java.awt.*;

//used for the dealer and player
 
public abstract class Person {

    private Hand hand;
    private String name;

    public Person(){
        this.hand = new Hand();
        this.name = "";
    }
    //Setters and Getters
    public Hand getHand(){
        return this.hand;
    }
    public void setHand(Hand hand){
        this.hand = hand;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }


    //update the image icons for the player's hand
     
    public void printHand(JLabel[] cardPics){
        System.out.println(this.name + "'s hand are:");
        System.out.println(this.hand + " points: " + this.hand.calculatedValue());

        //iterate through each card, update pic, hide remaining
        for(int i = 0; i < 11; i++){
            cardPics[i].setVisible(false);
        }
        for(int i = 0; i < this.hand.getHandSize(); i++){
            ImageIcon icon = this.hand.getCard(i).getIcon();
            if (icon != null) {
                cardPics[i].setIcon(new ImageIcon(icon.getImage().getScaledInstance(Game.CARD_WIDTH, Game.CARD_HEIGHT, Image.SCALE_SMOOTH)));
            } else {
                cardPics[i].setIcon(null);
            }
            cardPics[i].setVisible(true);
        }

    }

    //take a card from deck
    public void hit(Deck deck, Deck discard){
        //if there's no cards left in the deck, deck reset
        if (!deck.hasCards()) {
            deck.reset(discard);
        }
        this.hand.takeCardFromDeck(deck);
        System.out.println(this.name + " gets a card");
    }


   

}

