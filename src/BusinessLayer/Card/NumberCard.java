package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.CardColor;

public class NumberCard extends Card {
    private int number;

    public NumberCard(int number, CardColor color, Integer point) {
        super(color, point);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return super.getColor() + " " + number;
    }

    @Override
    public boolean canBePlayedOn(Card card) {
        if (card instanceof NumberCard) {
            NumberCard numberCard = (NumberCard) card;
            return numberCard.getColor().equals(this.getColor()) || numberCard.getNumber() == this.getNumber();
        }
        return false;
    }
}
