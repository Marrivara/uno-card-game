package src.BusinessLayer.Referee;

import java.util.ArrayList;
import java.util.List;


import src.BusinessLayer.Player.Player;
import src.BusinessLayer.Deck.DiscardPile;
import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.GameEnvironment.GameEnvironment;
import src.BusinessLayer.Card.ActionCard;
import src.BusinessLayer.Card.Card;

public class Referee {
    private GameRules rules;
    private Player currentPlayer;
    private GameEnvironment env;
    private GameStateRecorder recorder;
    private int dealerIndex;

    public Referee() {
        this.rules = new GameRules();
        this.env = new GameEnvironment();
        this.recorder= new GameStateRecorder();
        dealerIndex = 0;
    }

    public boolean playerTurn() {
        Card topCard = env.getDiscardPile().peekTopCard();
        Card playedCard = currentPlayer.playCard(topCard);

            if (playedCard == null) {
                System.out.println(currentPlayer.getName() + " has no playable cards and must draw.");

                if (env.getDrawPile().returnAllCards().isEmpty()) {
                    env.reshuffleDiscardToDraw();
                    Card drawnCard = currentPlayer.pickCard(env.getDrawPile() , 1).removeFirst();
                    System.out.println(currentPlayer.getName() + " draws " + drawnCard);

                    if (canPlayDrawnCard(drawnCard)) {
                        System.out.println(currentPlayer.getName() + " plays the drawn card: " + drawnCard);
                        env.getDiscardPile().pushCard(drawnCard);
                    }
                }
            }
            
            boolean skipped = false;
            if (playedCard instanceof ActionCard) {
                ActionCardEnum type = ((ActionCard)playedCard).getActionType();

                switch (type) {
                    case ActionCardEnum.DRAW_TWO:
                    env.getNextPlayer(currentPlayer).pickCard(env.getDrawPile(),2);
                    break;
                    case ActionCardEnum.REVERSE:
                    env.reverse();
                    break;
                    case ActionCardEnum.SKIP:
                    skipped = true;
                    break;
                    case ActionCardEnum.WILD:
                    currentPlayer.getMostCommonColor();
                    break;
                    case ActionCardEnum.WILD_DRAW_FOUR:
                    env.getNextPlayer(currentPlayer).pickCard(env.getDrawPile(), 4);
                    break;
                    case ActionCardEnum.SHUFFLE:
                    env.shuffle();
                }
            }
            
            if (currentPlayer.hasNoCards()) {
                System.out.println(currentPlayer.getName() + " has no cards left! Round over.");
                return true;
            }
            if (skipped)
                currentPlayer = env.skip(currentPlayer);
            else 
                currentPlayer = env.getNextPlayer(currentPlayer);
            
        return false;
    }
    public void playGame() {
        int roundNumber = 1;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\n========== ROUND " + roundNumber + " ==========");
            playRound();
        }
        for (Player p : env.getAllPlayers()) {
            if (p.getScore() >= rules.WINNING_SCORE) {
                gameOver = true;
                System.out.println("\n" + p.getName() + " won the game!");
                System.out.println("Score: " + p.getScore());
                break;
            }
            roundNumber++;

            if (!gameOver)
                resetForNewRound();
        }
    }
    private void playRound() {
        boolean roundOver = false;
       
        while (!roundOver) {
            System.out.println("\n" + currentPlayer.getName() + "'s turn");
            
            roundOver = playerTurn();
            
            if (roundOver) {
                calculateRoundPoints(currentPlayer, env.getAllPlayers());
                recorder.recordGameState(env.getAllPlayers(), "Round");
            }
        }
    }

    public void calculateRoundPoints(Player winner, List<Player> players) {
        int points = 0;
        
        // Calculate points from remaining cards in opponents' hands
        for (Player player : players) {
            if (player != winner) {
                for (Card card : player.getHand()) {
                    points += card.getPoint();
                }
            }
        }
        
        // Award points to the winner
        winner.addScore(points);
        System.out.println(winner.getName() + " wins the round and earns " + points + " points!");
        System.out.println("Current score: " + winner.getScore());
    }

    private boolean canPlayDrawnCard(Card drawnCard) {
        boolean hasMatchingColor = false;
        
        for (Card card : currentPlayer.getHand()) {
            if (card.getColor().equals(env.getDiscardPile().peekTopCard().getColor())) {
                hasMatchingColor = true;
                break;
            }
        }
        
        return rules.isCardPlayable(drawnCard, env.getDiscardPile().peekTopCard(), hasMatchingColor);
    }

    private void resetForNewRound() {
        // Clear all player hands
        for (Player p: env.getAllPlayers()) {
            p.getHand().clear();
        }
        
        env.shuffle();
        
        // Move dealer position to the next player
        dealerIndex = (dealerIndex + 1) % env.getAllPlayers().size();
        System.out.println("\nNew dealer: " + env.getAllPlayers().get(dealerIndex).getName());
        
        // Deal cards again
        dealInitialCards();
        
        // Start discard pile
        initializeDiscardPile();
        
        // Set first player
        setFirstPlayer();
    }

    private void dealInitialCards() {
        System.out.println("\n--- Dealing Initial Cards ---");
        for (int i = 0; i < rules.INITIAL_HAND_SIZE; i++) {
            for (int j = 0; j < env.getAllPlayers().size(); j++) {
                // Calculate player index starting from the left of the dealer
                int playerIndex = (dealerIndex + 1 + j) % env.getAllPlayers().size();
                Player currentPlayer = env.getAllPlayers().get(playerIndex);
                currentPlayer.pickCard(env.getDrawPile(), 1);
                
            }
        }
        
        // Print each player's hand
        for (Player player : env.getAllPlayers()) {
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }
    }

    private void initializeDiscardPile() {
        Card topCard = env.getDrawPile().drawCard(1).removeFirst();
        env.getDiscardPile().pushCard(topCard);
        
        System.out.println("\nStarting card on discard pile: " + topCard);
    }

    private void setFirstPlayer() {
        // TODO : her zaman index 0 başlıyor
        currentPlayer = env.getAllPlayers().getFirst();
    }
}
