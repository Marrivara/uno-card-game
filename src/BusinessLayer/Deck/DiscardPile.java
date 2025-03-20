public class DiscardPile extends Pile {

    public DiscardPile() {
        super();
    }

    public void cardAction(Card card) {
        cards.push(card);
        return;
    }
}