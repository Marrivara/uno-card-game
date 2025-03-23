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

}
