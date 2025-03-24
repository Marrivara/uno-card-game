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
    public String getName() {
        String str = new String();
        if (this instanceof ActionCard)
            str = ((ActionCard) this).getActionType().toString();
        else
            str = this.getPoint().toString();
        return str;
    }
        
}
