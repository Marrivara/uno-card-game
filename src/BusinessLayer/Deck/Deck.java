package src.BusinessLayer.Deck;

import src.BusinessLayer.Card.ActionCard;
import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Card.NumberCard;
import src.BusinessLayer.DataTypes.Bag;
import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.Enum.CardColor;

import java.util.ArrayList;
import java.util.List;


public class Deck {

    private Bag<Card> cards;

    public Deck() {
        cards = new Bag<Card>();
    }

    public void addAllCards(ArrayList<Card> cards) {
        this.cards.addAll(cards);
    }
}
