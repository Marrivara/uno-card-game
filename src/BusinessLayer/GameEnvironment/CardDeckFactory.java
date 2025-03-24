package src.BusinessLayer.GameEnvironment;

import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Card.NumberCard;
import src.BusinessLayer.Card.ActionCard;
import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

import java.util.ArrayList;

/**
 * The CardDeckFactory class is responsible for creating a complete deck of cards
 * according to the game rules.
 */
public class CardDeckFactory {

    /**
     * Creates a complete deck of cards for the Duo Card Game.
     * The deck consists of 109 cards:
     * - 76 number cards (0-9 in four colors)
     * - 33 action cards (Draw Two, Reverse, Skip, Wild, Wild Draw Four, Shuffle Hands)
     *
     * @return An ArrayList containing all 109 cards
     */
    public ArrayList<Card> createCompleteDeck() {
        ArrayList<Card> deck = new ArrayList<>();

        // Add number cards (76 cards)
        for (CardColor color : CardColor.values()) {
            // One '0' card per color
            deck.add(new NumberCard(color, 0));

            // Two of each 1-9 card per color
            for (int number = 1; number <= 9; number++) {
                deck.add(new NumberCard(color, number));
                deck.add(new NumberCard(color, number));
            }
        }

        // Add action cards (33 cards)
        for (CardColor color : CardColor.values()) {
            // Two Draw Two cards per color
            deck.add(new ActionCard(color, ActionCardEnum.DRAW_TWO, 20));
            deck.add(new ActionCard(color, ActionCardEnum.DRAW_TWO, 20));

            // Two Reverse cards per color
            deck.add(new ActionCard(color, ActionCardEnum.REVERSE, 20));
            deck.add(new ActionCard(color, ActionCardEnum.REVERSE, 20));

            // Two Skip cards per color
            deck.add(new ActionCard(color, ActionCardEnum.SKIP, 20));
            deck.add(new ActionCard(color, ActionCardEnum.SKIP, 20));

            // One Wild card per color
            deck.add(new ActionCard(color, ActionCardEnum.WILD, 50));

            // One Wild Draw Four card per color
            deck.add(new ActionCard(color, ActionCardEnum.WILD_DRAW_FOUR, 50));
        }

        // Add Shuffle Hands card (1 card)
        deck.add(new ActionCard(CardColor.BLACK, ActionCardEnum.SHUFFLE, 40));

        return deck;
    }
}