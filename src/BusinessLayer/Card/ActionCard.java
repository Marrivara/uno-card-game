package src.BusinessLayer.Card;

import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

public class ActionCard extends Card{
    private final ActionCardEnum action;

    public ActionCard(ActionCardEnum action, CardColor color, int number){
        super(color, number);
        this.action = action;
    }

    @Override
    public boolean canBePlayedOn(Card card, CardColor nextColor) {
        // If action is Wild, WildDrawFour, Shuffle, return true,
        // if action is others, check if color match, or if card is also same action type card
        // if new card is not action card, only check color
        if (card instanceof ActionCard actionCard) {
            if (actionCard.getAction() == ActionCardEnum.WILD || actionCard.getAction() == ActionCardEnum.WILD_DRAW_FOUR || actionCard.getAction() == ActionCardEnum.SHUFFLE) {
                return this.getColor().equals(nextColor);
            }
            return actionCard.getColor().equals(this.getColor()) || actionCard.getAction().equals(this.getAction());
        }
        return card.getColor().equals(this.getColor());
    }

    public ActionCardEnum getAction() {
        return action;
    }

    @Override
    public String toString() {
        return super.getColor() + " " + action;
    }
}
