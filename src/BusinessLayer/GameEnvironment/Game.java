package src.BusinessLayer.GameEnvironment;

import src.BusinessLayer.Player.Player;
import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Referee.GameStateRecorder;

import java.util.Random;

/**
 * The Game class manages the entire game flow.
 */
public class Game {
    private GameEnvironment gameEnvironment;
    private GameInitializer gameInitializer;
    private int currentPlayerIndex;
    private boolean gameDirection; // true for left, false for right
    private Random random;
    private static final int WINNING_SCORE = 500;

    /**
     * Creates a new game.
     */
    public Game() {
        gameEnvironment = new GameEnvironment();
        random = new Random();

        // Create players (2-4 players randomly)
        int numPlayers = random.nextInt(3) + 2; // 2 to 4 players
        for (int i = 0; i < numPlayers; i++) {
            gameEnvironment.addPlayer(new Player("Player" + (i + 1)));
        }
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        System.out.println("Starting Duo Card Game...");

        // Initialize the game
        gameInitializer = new GameInitializer(gameEnvironment);
        gameInitializer.initializeGame();

        // Set initial game state from initializer
        int dealerIndex = gameInitializer.getDealerIndex();
        gameDirection = gameInitializer.getGameDirection();

        // Set the first player (player to the left of dealer or based on first card's effect)
        currentPlayerIndex = (dealerIndex + 1) % gameEnvironment.getAllPlayers().size();

        // Record initial game state
        GameStateRecorder.recordGameState(gameEnvironment.getAllPlayers(), "Initial");

        // Play the game
        playGame();
    }

    /**
     * Plays the game until a player reaches 500 points.
     */
    private void playGame() {
        int roundNumber = 1;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\n========== ROUND " + roundNumber + " ==========");
            playRound();

            // Check if any player has reached 500 points
            for (Player player : gameEnvironment.getAllPlayers()) {
                if (player.getScore() >= WINNING_SCORE) {
                    gameOver = true;
                    System.out.println("\n" + player.getName() + " won the game!");
                    System.out.println("Score: " + player.getScore());
                    break;
                }
            }

            roundNumber++;

            // Reset for next round if game is not over
            if (!gameOver) {
                resetForNewRound();
            }
        }

        // Record final game state
        GameStateRecorder.recordGameState(gameEnvironment.getAllPlayers(), "Final");
    }

    /**
     * Plays a single round of the game.
     */
    private void playRound() {
        boolean roundOver = false;
        TurnManager turnManager = new TurnManager(gameEnvironment);

        while (!roundOver) {
            Player currentPlayer = gameEnvironment.getAllPlayers().get(currentPlayerIndex);
            System.out.println("\n" + currentPlayer.getName() + "'s turn");

            // Current player plays their turn
            roundOver = turnManager.playerTurn(currentPlayer);

            if (roundOver) {
                // Calculate and award points to the winner
                calculateRoundPoints(currentPlayer);
                GameStateRecorder.recordGameState(gameEnvironment.getAllPlayers(), "Round");
                break;
            }

            // Move to the next player
            moveToNextPlayer();
        }
    }

    /**
     * Calculates and awards points to the winner of a round.
     *
     * @param winner The player who won the round
     */
    private void calculateRoundPoints(Player winner) {
        int points = 0;

        // Calculate points from remaining cards in opponents' hands
        for (Player player : gameEnvironment.getAllPlayers()) {
            if (player != winner) {
                for (Card card : player.getHand()) {
                    points += card.getValue();
                }
            }
        }

        // Award points to the winner
        winner.addScore(points);
        System.out.println(winner.getName() + " wins the round and earns " + points + " points!");
        System.out.println("Current score: " + winner.getScore());
    }

    /**
     * Moves to the next player based on the game direction.
     */
    private void moveToNextPlayer() {
        if (gameDirection) {
            // Left direction
            currentPlayerIndex = (currentPlayerIndex + 1) % gameEnvironment.getAllPlayers().size();
        } else {
            // Right direction
            currentPlayerIndex = (currentPlayerIndex - 1 + gameEnvironment.getAllPlayers().size()) % gameEnvironment.getAllPlayers().size();
        }
    }

    /**
     * Resets the game for a new round.
     */
    private void resetForNewRound() {
        // Clear all player hands and reset decks
        for (Player player : gameEnvironment.getAllPlayers()) {
            player.returnAllCards().clear();
        }

        // Reinitialize the game
        gameInitializer = new GameInitializer(gameEnvironment);
        gameInitializer.initializeGame();

        // Update dealer index (next player becomes dealer)
        int dealerIndex = gameInitializer.getDealerIndex();
        gameDirection = gameInitializer.getGameDirection();

        // Set the first player for the new round
        currentPlayerIndex = (dealerIndex + 1) % gameEnvironment.getAllPlayers().size();
    }
}