package src.BusinessLayer.GameEnvironment;

import src.BusinessLayer.Player.Player;
import src.BusinessLayer.Card.Card;
import src.BusinessLayer.Card.ActionCard;
import src.BusinessLayer.Card.NumberCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The GameInitializer class is responsible for initializing the game.
 * It sets up players, determines the dealer, creates and deals cards,
 * and handles the initial discard pile setup.
 */
public class GameInitializer {
    private GameEnvironment gameEnvironment;
    private Random random;
    private CardDeckFactory deckFactory;
    private int dealerIndex;
    private ArrayList<Player> players;
    private boolean gameDirection; // true for left, false for right
    private static final int INITIAL_HAND_SIZE = 7;

    /**
     * Creates a new game initializer for the given game environment.
     *
     * @param gameEnvironment The game environment to initialize
     */
    public GameInitializer(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
        this.random = new Random();
        this.deckFactory = new CardDeckFactory();
        this.gameDirection = true; // Default direction is left
        this.players = gameEnvironment.getAllPlayers();
    }

    /**
     * Initializes the game by creating players, determining the dealer,
     * creating the card deck, dealing cards, and setting up the initial discard pile.
     */
    public void initializeGame() {
        // Validate we have enough players
        if (players.size() < 2 || players.size() > 4) {
            throw new IllegalStateException("Game requires 2-4 players. Current count: " + players.size());
        }

        System.out.println("Starting game with " + players.size() + " players.");

        // Create and initialize the card deck
        createCardDeck();

        // Determine dealer
        dealerIndex = determineDealer();

        // Deal cards to players
        dealInitialCards();

        // Setup initial discard pile
        setupInitialDiscardPile();
    }

    /**
     * Creates the card deck with all 109 cards as specified in the game rules.
     */
    private void createCardDeck() {
        ArrayList<Card> allCards = deckFactory.createCompleteDeck();
        Collections.shuffle(allCards);

        gameEnvironment.getInitialDeck().addAllCards(allCards);

        System.out.println("Card deck created and shuffled with " + allCards.size() + " cards.");
    }

    /**
     * Determines the dealer by having each player draw a card.
     * The player with the highest value card becomes the dealer.
     *
     * @return The index of the dealer in the players list
     */
    private int determineDealer() {
        System.out.println("\n--- Determining Dealer ---");
        ArrayList<Card> drawnCards = new ArrayList<>();

        // Each player draws a card
        for (Player player : players) {
            Card card = gameEnvironment.getDrawPile().drawCard();
            drawnCards.add(card);
            System.out.println(player.getName() + " drew " + card + " to determine dealer.");
        }

        // Find the player with the highest card value
        int highestValue = -1;
        int highestIndex = 0;

        for (int i = 0; i < drawnCards.size(); i++) {
            int cardValue = drawnCards.get(i).getValue();
            if (cardValue > highestValue) {
                highestValue = cardValue;
                highestIndex = i;
            }
        }

        System.out.println(players.get(highestIndex).getName() + " will be the dealer.");

        // Return drawn cards to the deck
        for (Card card : drawnCards) {
            gameEnvironment.getDrawPile().pushCard(card);
        }

        // Shuffle the deck again
        ArrayList<Card> allCards = gameEnvironment.getDrawPile().returnAllCards();
        Collections.shuffle(allCards);
        gameEnvironment.getDrawPile().addDeck(allCards);

        return highestIndex;
    }

    /**
     * Deals 7 cards to each player starting from the player to the left of the dealer.
     */
    private void dealInitialCards() {
        System.out.println("\n--- Dealing Initial Cards ---");

        // Deal 7 cards to each player starting from the left of the dealer
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            for (int j = 0; j < players.size(); j++) {
                // Calculate player index starting from the left of the dealer
                int playerIndex = (dealerIndex + 1 + j) % players.size();
                Player currentPlayer = players.get(playerIndex);

                Card card = gameEnvironment.getDrawPile().drawCard();
                currentPlayer.addCard(card);
            }
        }

        // Print each player's hand
        for (Player player : players) {
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }
    }

    /**
     * Sets up the initial discard pile by drawing the top card from the draw pile
     * and handling any special effects if it's an action card.
     */
    private void setupInitialDiscardPile() {
        Card topCard = gameEnvironment.getDrawPile().drawCard();
        gameEnvironment.getDiscardPile().pushCard(topCard);

        System.out.println("\nStarting card on discard pile: " + topCard);

        // Handle special cases based on the first card
        if (topCard instanceof ActionCard) {
            ActionCard actionCard = (ActionCard) topCard;
            String actionType = actionCard.getActionType();

            handleInitialActionCard(actionType);
        }
    }

    /**
     * Handles the effect of an action card when it's the first card in the discard pile.
     *
     * @param actionType The type of action card
     */
    private void handleInitialActionCard(String actionType) {
        // Get the player to the left of the dealer
        int firstPlayerIndex = (dealerIndex + 1) % players.size();
        Player firstPlayer = players.get(firstPlayerIndex);

        switch (actionType) {
            case "DrawTwo":
                // First player draws two cards and skips their turn
                System.out.println(firstPlayer.getName() + " draws 2 cards and skips their turn due to Draw Two card.");
                firstPlayer.addCard(gameEnvironment.getDrawPile().drawCard());
                firstPlayer.addCard(gameEnvironment.getDrawPile().drawCard());
                firstPlayerIndex = (firstPlayerIndex + 1) % players.size(); // Skip to the next player
                break;

            case "Reverse":
                // Reverse the order of play, so dealer plays first
                System.out.println("Game direction is reversed due to Reverse card. Dealer plays first.");
                gameDirection = false;
                gameEnvironment.reverse(); // Reverse the player list
                firstPlayerIndex = dealerIndex; // Dealer plays first
                break;

            case "Skip":
                // First player is skipped
                System.out.println(firstPlayer.getName() + " is skipped due to Skip card.");
                firstPlayerIndex = (firstPlayerIndex + 1) % players.size(); // Skip to the second player
                break;

            case "Wild":
                // First player chooses color (in this implementation, we'll pick their most common color)
                String newColor = getMostCommonColor(firstPlayer);
                Card topCard = gameEnvironment.getDiscardPile().peekTopCard();
                if (topCard instanceof ActionCard) {
                    ((ActionCard) topCard).setColor(newColor);
                }
                System.out.println(firstPlayer.getName() + " chooses " + newColor + " as the starting color due to Wild card.");
                break;

            case "WildDrawFour":
                // Put it back and draw a new card
                System.out.println("Wild Draw Four drawn as first card. Putting back and drawing a new one.");
                Card wildDrawFour = gameEnvironment.getDiscardPile().peekTopCard();
                gameEnvironment.getDiscardPile().removeTopCard();
                gameEnvironment.getDrawPile().pushCard(wildDrawFour);

                // Shuffle and draw a new card
                ArrayList<Card> drawPileCards = gameEnvironment.getDrawPile().returnAllCards();
                Collections.shuffle(drawPileCards);
                gameEnvironment.getDrawPile().addDeck(drawPileCards);

                Card newTopCard = gameEnvironment.getDrawPile().drawCard();
                gameEnvironment.getDiscardPile().pushCard(newTopCard);
                System.out.println("New starting card: " + newTopCard);

                // Handle recursively if the new card is also an action card
                if (newTopCard instanceof ActionCard) {
                    handleInitialActionCard(((ActionCard) newTopCard).getActionType());
                }
                break;

            case "ShuffleHands":
                // First player chooses color
                newColor = getMostCommonColor(firstPlayer);
                topCard = gameEnvironment.getDiscardPile().peekTopCard();
                if (topCard instanceof ActionCard) {
                    ((ActionCard) topCard).setColor(newColor);
                }
                System.out.println(firstPlayer.getName() + " chooses " + newColor + " as the starting color due to Shuffle Hands card.");
                break;
        }

        // Set the current player based on the first card's effect
        System.out.println("First player will be: " + players.get(firstPlayerIndex).getName());
        System.out.println("Game direction: " + (gameDirection ? "Left" : "Right"));
    }

    /**
     * Gets the most common color in a player's hand.
     *
     * @param player The player
     * @return The most common color
     */
    private String getMostCommonColor(Player player) {
        int blueCount = 0, greenCount = 0, redCount = 0, yellowCount = 0;

        for (Card card : player.getHand()) {
            String color = card.getColor();
            switch (color) {
                case "Blue": blueCount++; break;
                case "Green": greenCount++; break;
                case "Red": redCount++; break;
                case "Yellow": yellowCount++; break;
            }
        }

        int maxCount = Math.max(Math.max(blueCount, greenCount), Math.max(redCount, yellowCount));

        if (blueCount == maxCount) return "Blue";
        if (greenCount == maxCount) return "Green";
        if (redCount == maxCount) return "Red";
        return "Yellow";
    }

    /**
     * Gets the dealer index.
     *
     * @return The dealer index
     */
    public int getDealerIndex() {
        return dealerIndex;
    }

    /**
     * Gets the game direction.
     *
     * @return The game direction (true for left, false for right)
     */
    public boolean getGameDirection() {
        return gameDirection;
    }
}