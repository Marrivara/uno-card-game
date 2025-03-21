package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.CardColor;

public abstract class Card {
    private CardColor color;
    private Integer point;

    public Card(CardColor color, Integer point) {
        this.color = color;
        this.point = point;
    }

    public CardColor getColor() {
        return color;
    }

    public Integer getPoint() {
        return point;
    }

    public abstract boolean canBePlayedOn(Card card, CardColor nextColor);

    public abstract String toString();
}
