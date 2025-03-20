public class DrawPile extends Pile {
    
    public DrawPile() {
        super();
    }

    public Card drawCard() {
        return cards.pop();
    }
}