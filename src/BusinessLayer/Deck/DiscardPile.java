package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.Card;

public class DiscardPile extends Pile {

    public DiscardPile() {
        super();
    }

    public void pushCard(Card c) {
        this.getStack().push(c);
    }

    public Card peekTopCard() {
        return this.getStack().peek();
    }
}