package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.Card;

import java.util.Stack;
import java.util.ArrayList;

public abstract class Pile {

    private Stack<Card> cards;

    public Pile() {
        cards = new Stack<Card>();
    }

    public ArrayList<Card> returnAllCards() {
        return new ArrayList<Card>(cards);
    }

    protected Stack<Card> getStack() {
        return cards;
    }

    protected void setStack(ArrayList<Card> deck) {
        for (Card c : deck) {
            cards.push(c);
        }
    }
}