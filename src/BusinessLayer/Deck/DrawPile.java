package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.Card;

public class DrawPile extends Pile {
    
    public DrawPile() {
        super();
    }

    public Card drawCard() {
        return this.getCards().pop();
    }
}