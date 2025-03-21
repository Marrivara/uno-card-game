package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.Card;

public class DiscardPile extends Pile {

    public DiscardPile() {
        super();
    }

    public void cardAction(Card card) {
        this.getCards().push(card);
        return;
    }
}