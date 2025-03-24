package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.CardColor;

public class NumberCard extends Card {
    private int number;

    public NumberCard(CardColor color, int number) {
        super(color, number);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
