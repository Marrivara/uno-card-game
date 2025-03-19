package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

public class ActionCard extends Card{
    private ActionCardEnum action;

    public ActionCard(ActionCardEnum action, CardColor color, int number){
        super(color, number);
        this.action = action;
    }


    @Override
    public boolean canBePlayedOn(Card card) {

        if (card instanceof ActionCard) {
            ActionCard actionCard = (ActionCard) card;
            return actionCard.getColor().equals(this.getColor()) || actionCard.getAction().equals(this.getAction());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.getColor() + " " + action;
    }
}
