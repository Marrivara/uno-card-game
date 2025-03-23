package src.BusinessLayer.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.BusinessLayer.Card.*;
import src.BusinessLayer.Enum.ActionCardEnum;

/**
 * The CardStrategy class implements strategy for choosing which card to play.
 */
public class CardStrategy {
    private Random random;
    
    /**
     * Creates a new card strategy with the given random generator.
     * 
     * @param random The random number generator
     */
    public CardStrategy() {
        this.random = new Random();
    }
    
    /**
     * Chooses a card to play based on the rules.
     * 
     * @param playableCards The list of playable cards
     * @param topCard The top card on the discard pile
     * @return The chosen card to play
     */
    public Card chooseCardToPlay(List<Card> playableCards, Card topCard) {
        // Check if Wild or Shuffle Hands is in playable cards (can always be played)
        for (Card card : playableCards) {
            if (card instanceof ActionCard) {
                ActionCard actionCard = (ActionCard) card;
                if (actionCard.getActionType().equals(ActionCardEnum.WILD) || 
                    actionCard.getActionType().equals(ActionCardEnum.SHUFFLE) ||
                    actionCard.getActionType().equals(ActionCardEnum.WILD_DRAW_FOUR)) {
                    return card;
                }
            }
        }
        
        // Randomly decide whether to play a card matching the number or the color
        // Get all cards matching the color and all cards matching the number/action
        List<Card> colorMatches = new ArrayList<>();
        List<Card> numberOrActionMatches = new ArrayList<>();
        
        for (Card card : playableCards) {
            if (card.getColor().equals(topCard.getColor())) {
                colorMatches.add(card);
            } else if (topCard instanceof NumberCard && card instanceof NumberCard) {
                NumberCard numCard = (NumberCard) card;
                NumberCard topNumCard = (NumberCard) topCard;
                if (numCard.getNumber() == topNumCard.getNumber()) {
                    numberOrActionMatches.add(card);
                }
            } else if (topCard instanceof ActionCard && card instanceof ActionCard) {
                ActionCard actionCard = (ActionCard) card;
                ActionCard topActionCard = (ActionCard) topCard;
                if (actionCard.getActionType().equals(topActionCard.getActionType())) {
                    numberOrActionMatches.add(card);
                }
            }
        }
        
        // Randomly choose between color match and number/action match if both are available
        if (!colorMatches.isEmpty() && !numberOrActionMatches.isEmpty()) {
            if (random.nextBoolean()) {
                // Choose highest value color match
                return getHighestValueCard(colorMatches);
            } else {
                // Choose a random number/action match
                return numberOrActionMatches.get(random.nextInt(numberOrActionMatches.size()));
            }
        } else if (!colorMatches.isEmpty()) {
            // Choose highest value color match
            return getHighestValueCard(colorMatches);
        } else if (!numberOrActionMatches.isEmpty()) {
            // Choose a random number/action match
            return numberOrActionMatches.get(random.nextInt(numberOrActionMatches.size()));
        } else {
            // Return a random playable card (should not happen with current logic)
            return playableCards.get(random.nextInt(playableCards.size()));
        }
    }
    
    /**
     * Gets the card with the highest value from a list of cards.
     * 
     * @param cards The list of cards
     * @return The card with the highest value
     */
    private Card getHighestValueCard(List<Card> cards) {
        Card highestCard = cards.get(0);
        int highestValue = highestCard.getPoint();
        
        for (Card card : cards) {
            if (card.getPoint() > highestValue) {
                highestValue = card.getPoint();
                highestCard = card;
            }
        }
        
        return highestCard;
    }
}
   
