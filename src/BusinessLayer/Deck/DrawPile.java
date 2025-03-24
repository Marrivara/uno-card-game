package src.BusinessLayer.Deck;

import java.util.ArrayList;

import src.BusinessLayer.Card.Card;

public class DrawPile extends Pile {
    
    public DrawPile() {
        super();
    }

    public void addDeck(ArrayList<Card> deck) {
        setStack(deck);
    }



    public ArrayList<Card> drawCard(int amount) {
        ArrayList<Card> temp = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (!this.getStack().isEmpty()) {
                temp.add(this.getStack().pop());
            }
            else 
                throw new IllegalStateException("There is no card in the pile!");
        }
        return temp;
    }
    
}