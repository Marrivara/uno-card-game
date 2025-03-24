package src.BusinessLayer.Referee;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import src.BusinessLayer.Player.Player;
import src.BusinessLayer.Enum.ActionCardEnum;
import src.BusinessLayer.GameEnvironment.GameEnvironment;
import src.BusinessLayer.Deck.*;
import src.BusinessLayer.Card.*;

public class Referee {
    private GameRules rules;
    private Player currentPlayer;
    private GameEnvironment env;
    private GameStateRecorder recorder;
    private int dealerIndex;
    private int numberOfPlayers;
    private Deck deck;

    public Referee() {
        this.rules = new GameRules();
        this.env = new GameEnvironment();
        this.recorder = new GameStateRecorder();
        deck = new Deck();
        dealerIndex = 0;

        Random rand = new Random();
        numberOfPlayers = rand.nextInt(3) + 2;
        for (int i = 0; i < numberOfPlayers; i++) {
            Player newPlayer = new Player("Player" + i);
            env.addPlayer(newPlayer);
        }
        env.getDrawPile().addDeck(deck.getDeck());
    }

    public boolean playerTurn() {
        Card topCard = env.getDiscardPile().peekTopCard();
        System.out.println("Top card: " + topCard.getName());
        Card playedCard = currentPlayer.playCard(topCard);
        env.getDiscardPile().returnAllCards().addLast(playedCard);
        System.out.println("Played card: " + playedCard);

        if (playedCard == null && !currentPlayer.getHand().isEmpty()) {
            System.out.println(currentPlayer.getName() + " has no playable cards and must draw.");
            System.out.println(env.getDrawPile().returnAllCards().size());

            if (env.getDrawPile().returnAllCards().isEmpty())
                env.reshuffleDiscardToDraw();
            Card drawnCard = currentPlayer.pickCard(env.getDrawPile(), 1).removeFirst();
            env.getDrawPile().returnAllCards().remove(drawnCard);
            System.out.println(currentPlayer.getName() + " draws " + drawnCard);

            if (canPlayDrawnCard(drawnCard)) {
                System.out.println(currentPlayer.getName() + " plays the drawn card: " + drawnCard);
                env.getDiscardPile().pushCard(drawnCard);
            }
        } else if (!currentPlayer.getHand().isEmpty()) {
            System.out.println(currentPlayer.getName() + " plays " + playedCard.getName());
            env.getDiscardPile().pushCard(playedCard);
        }

        boolean skipped = false;
        if (playedCard instanceof ActionCard) {
            ActionCardEnum type = ((ActionCard) playedCard).getActionType();

            switch (type) {
                case ActionCardEnum.DRAW_TWO:
                    env.getNextPlayer(currentPlayer).pickCard(env.getDrawPile(), 2);
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
                    shuffle();
                    break;
            }
        }
        System.out.println(currentPlayer.getName() + currentPlayer.getHand().size());
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

    private void shuffle() {
        Player tempCurrentPlayer = currentPlayer;
        currentPlayer = env.getNextPlayer(currentPlayer);
        DrawPile pile = env.shuffle();

        while (!pile.returnAllCards().isEmpty()) {
            currentPlayer.pickCard(pile, 1);
            currentPlayer = env.getNextPlayer(currentPlayer);
        }
        currentPlayer = tempCurrentPlayer;
    }

    public void playGame() {
        int roundNumber = 1;
        boolean gameOver = false;

        this.setFirstPlayer();
        this.dealInitialCards();
        this.initializeDiscardPile();

        while (!gameOver) {
            System.out.println("\n========== ROUND " + roundNumber + " ==========");
            playRound();
            for (Player p : env.getAllPlayers()) {
                if (p.getScore() >= GameRules.WINNING_SCORE) {
                    gameOver = true;
                    System.out.println("\n" + p.getName() + " won the game!");
                    System.out.println("Score: " + p.getScore());
                    break;
                }
                roundNumber++;

                Scanner scanner = new Scanner(System.in);
                System.out.println("Press anywhere to continue...");
                scanner.nextLine();
                scanner.close();

                resetForNewRound();
            }
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
        for (Player p : env.getAllPlayers()) {
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
        for (int i = 0; i < GameRules.INITIAL_HAND_SIZE; i++) {
            for (int j = 0; j < env.getAllPlayers().size(); j++) {
                // Calculate player index starting from the left of the dealer
                int playerIndex = (dealerIndex + 1 + j) % env.getAllPlayers().size();
                Player currentPlayer = env.getAllPlayers().get(playerIndex);
                currentPlayer.pickCard(env.getDrawPile(), 1);

            }
        }

        // Print each player's hand
        for (Player player : env.getAllPlayers()) {
            for (Card c : player.getHand()) {
                System.out.println(player.getName() + "'s hand: " + c.getName());
            }
        }
    }

    private void initializeDiscardPile() {
        Card topCard = env.getDrawPile().drawCard(1).removeFirst();
        env.getDiscardPile().pushCard(topCard);

        System.out.println("\nStarting card on discard pile: " + topCard.getName());
    }

    private void setFirstPlayer() {
        // TODO : her zaman index 0 başlıyor
        currentPlayer = env.getAllPlayers().getFirst();
    }
}
