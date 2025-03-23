package src.BusinessLayer.Referee;

import src.BusinessLayer.Card.*;
import src.BusinessLayer.Enum.ActionCardEnum;

/**
 * The GameRules class contains constants and methods related to the rules of the Duo Card Game.
 */
public class GameRules {
    // Game constants
    public static final int WINNING_SCORE = 500;
    public static final int INITIAL_HAND_SIZE = 7;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    
    /**
     * Determines if a card can be played on the current top card.
     * 
     * @param card The card to check
     * @param topCard The top card on the discard pile
     * @param hasMatchingColor Whether the player has any cards matching the top card's color
     * @return true if the card can be played, false otherwise
     */
    public boolean isCardPlayable(Card card, Card topCard, boolean hasMatchingColor) {
        // Wild and Shuffle Hands can always be played
        if (card instanceof ActionCard) {
            ActionCard actionCard = (ActionCard) card;
            ActionCardEnum actionType = actionCard.getActionType();
            
            if (actionType.equals(ActionCardEnum.WILD) || actionType.equals(ActionCardEnum.SHUFFLE)) {
                return true;
            }
            
            // Wild Draw Four can only be played if no card of the current color is in hand
            if (actionType.equals(ActionCardEnum.WILD_DRAW_FOUR)) {
                return !hasMatchingColor;
            }
        }
        
        // Check if colors match
        if (card.getColor().equals(topCard.getColor())) {
            return true;
        }
        
        // Check if numbers match for NumberCards
        if (card instanceof NumberCard && topCard instanceof NumberCard) {
            NumberCard numCard = (NumberCard) card;
            NumberCard topNumCard = (NumberCard) topCard;
            if (numCard.getNumber() == topNumCard.getNumber()) {
                return true;
            }
        }
        
        // Check if action types match for ActionCards
        if (card instanceof ActionCard && topCard instanceof ActionCard) {
            ActionCard actionCard = (ActionCard) card;
            ActionCard topActionCard = (ActionCard) topCard;
            if (actionCard.getActionType().equals(topActionCard.getActionType())) {
                return true;
            }
        }
        
        return false;
    }
}
