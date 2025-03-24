package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.ActionCard;
import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Card.NumberCard;
import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

import java.util.ArrayList;
import java.util.List;


public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        deck.addAll(createNumberCards());
        deck.addAll(createActionCards());
    }

    private List<Card> createNumberCards() {
        List<Card> numberCards = new ArrayList<>();

        for (int j = 1; j < 10; j++) {
            for (CardColor color : CardColor.values()) {
                numberCards.add(new NumberCard(j, color, j));
                numberCards.add(new NumberCard(j, color, j));
            }
        }

        for (CardColor color : CardColor.values()) {
            numberCards.add(new NumberCard(0, color, 0));
        }

        return numberCards;
    }

    private List<Card> createActionCards() {
        List<Card> actionCards = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            for (CardColor color : CardColor.values()) {
                actionCards.add(new ActionCard(ActionCardEnum.DRAW_TWO, color, 20));
                actionCards.add(new ActionCard(ActionCardEnum.REVERSE, color, 20));
                actionCards.add(new ActionCard(ActionCardEnum.SKIP, color, 20));
            }
        }

        for (CardColor color : CardColor.values()) {
            actionCards.add(new ActionCard(ActionCardEnum.WILD, color, 50));
            actionCards.add(new ActionCard(ActionCardEnum.WILD_DRAW_FOUR, color, 50));
        }

        actionCards.add(new ActionCard(ActionCardEnum.SHUFFLE, CardColor.BLACK, 40));
        return actionCards;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}
