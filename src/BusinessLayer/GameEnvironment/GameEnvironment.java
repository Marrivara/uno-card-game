package src.BusinessLayer.GameEnvironment;

import src.BusinessLayer.Player.Player;

import src.BusinessLayer.Deck.*;
import src.BusinessLayer.Card.Card;
import java.util.ArrayList;
import java.util.Collections;

public class GameEnvironment {
    private ArrayList<Player> player_list;
    private DiscardPile discardPile;
    private DrawPile drawPile;
    
    private boolean validGame;
    

    public GameEnvironment() {
        player_list = new ArrayList<>();
        discardPile = new DiscardPile();
        drawPile = new DrawPile();

        validGame = false;
    }

    public void addPlayer(Player player) {
        player_list.add(player);
        validGame = player_list.size() >= 2;
    }
    
    public Player getNextPlayer(Player currentPlayer) {
        checkExceptions("getNextPlayer");
        
        int index = player_list.indexOf(currentPlayer);
        if (index == -1) {
            throw new IllegalArgumentException("Current player not found in the game!");
        }
        return player_list.get((index + 1) % player_list.size());
    }

    public ArrayList<Player> getAllPlayers() {
        return player_list;
    }

    public void reverse() {
        checkExceptions("reverse");
        Collections.reverse(player_list);
    }

    public Player skip(Player currentPlayer) {
        checkExceptions("skip");
        return getNextPlayer(getNextPlayer(currentPlayer));
    }

    public ArrayList<Card> shuffle() {
        checkExceptions("shuffle");

        ArrayList<Card> deck = new ArrayList<>();
        
        deck.addAll(this.discardPile.returnAllCards());
        deck.addAll(this.drawPile.returnAllCards());

        for (Player p : player_list) {
            deck.addAll(p.returnAllCards());
        }

        Collections.shuffle(deck);
        return deck;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public void reshuffleDiscardToDraw() {
        System.out.println("Draw pile is empty. Reshuffling discard pile to create a new draw pile.");
        
        Card topCard = discardPile.returnAllCards().removeLast();
        Collections.shuffle(discardPile.returnAllCards());
        drawPile.addDeck(discardPile.returnAllCards());
        discardPile.pushCard(topCard);
    }


    private void checkExceptions(String e) {

        if (!validGame) {
            throw new IllegalStateException(e + ": Game is not valid!");
        }
    }
}
