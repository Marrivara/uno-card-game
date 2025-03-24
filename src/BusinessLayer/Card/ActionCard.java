package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

public class ActionCard extends Card{
    private final ActionCardEnum action;
    public ActionCard(ActionCardEnum action, CardColor color, int number){
        super(color, number);
        this.action = action;
    }

    public ActionCardEnum getActionType() {
        return action;
    }

    public String toString() {
        return "ActionCard{" +
                "action=" + action +
                ", color=" + getColor() +
                ", point=" + getPoint() +
                '}';
    }
}
