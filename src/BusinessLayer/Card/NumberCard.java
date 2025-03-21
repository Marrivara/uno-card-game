package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.ActionCardEnum;
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
    public boolean canBePlayedOn(Card card, CardColor nextColor) {
        if (card instanceof ActionCard actionCard) {
            if (actionCard.getAction() == ActionCardEnum.WILD || actionCard.getAction() == ActionCardEnum.WILD_DRAW_FOUR || actionCard.getAction() == ActionCardEnum.SHUFFLE) {
                return this.getColor().equals(nextColor);
            }
            return this.getColor().equals(card.getColor());
        }
        else if (card instanceof NumberCard numberCard) {
            return numberCard.getColor().equals(this.getColor()) || numberCard.getNumber() == this.getNumber();
        }
        return false;
    }
}
