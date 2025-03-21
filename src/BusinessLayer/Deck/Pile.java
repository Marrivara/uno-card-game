package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.Card;

import java.util.Stack;

public abstract class Pile {

    private Stack<Card> cards;

    public Pile() {
        cards = new Stack<Card>();
    }

    public Stack<Card> getCards() {
        return cards;
    }
}