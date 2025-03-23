package src.BusinessLayer.Player;

import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Deck.*;
import src.BusinessLayer.Enum.CardColor;
import src.BusinessLayer.Referee.GameRules;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> validCards;
    private int score;
    private CardStrategy strategy;
    private GameRules rules;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
        strategy = new CardStrategy();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Card playCard(Card topCard) {
        findValidCards(topCard);
        
        if(!validCards.isEmpty()) {
            Card cardToPlay = strategy.chooseCardToPlay(validCards, topCard);
            System.out.println(this.getName() + " plays " + cardToPlay);
            hand.remove(cardToPlay);
            return cardToPlay;
        }
        return null;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    public ArrayList<Card> pickCard(DrawPile pile, int amount) {
        ArrayList<Card> temp = pile.drawCard(amount);
        hand.addAll(temp);
        return temp;
    }
    
    public ArrayList<Card> returnAllCards() {
        ArrayList<Card> temp = hand;
        hand.clear();
        return temp;
    }

    public CardColor getMostCommonColor() {
        int blueCount = 0, greenCount = 0, redCount = 0, yellowCount = 0;
        
        for (Card card : hand) {
            CardColor color = card.getColor();
            if (color.equals(CardColor.BLUE)) blueCount++;
            else if (color.equals(CardColor.GREEN)) greenCount++;
            else if (color.equals(CardColor.RED)) redCount++;
            else if (color.equals(CardColor.YELLOW)) yellowCount++;
        }
        
        int maxCount = Math.max(Math.max(blueCount, greenCount), Math.max(redCount, yellowCount));
        
        if (blueCount == maxCount) return CardColor.BLUE;
        if (greenCount == maxCount) return CardColor.GREEN;
        if (redCount == maxCount) return CardColor.RED;
        return CardColor.YELLOW;
    }

    private void findValidCards(Card topCard) {
        validCards.clear();
        boolean hasMatchingColor = false;
        for (Card card : this.getHand()) {
            if (card.getColor().equals(topCard.getColor())) {
                hasMatchingColor = true;
                break;
            }
        }

        for (Card card : this.getHand()) {
            if (rules.isCardPlayable(card, topCard, hasMatchingColor)) {
                validCards.add(card);
            }
        }  
    } 
}