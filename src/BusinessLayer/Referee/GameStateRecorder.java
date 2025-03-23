package src.BusinessLayer.Referee;

import src.BusinessLayer.Player.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The GameStateRecorder class is responsible for recording game states to a CSV file.
 */
public class GameStateRecorder {

    private static final String CSV_FILE_PATH = "game_state.csv";
    
    /**
     * Records the current game state to a CSV file.
     * 
     * @param players The list of players
     * @param stateType The type of state being recorded (Initial, Round, Final)
     */
    public void recordGameState(List<Player> players, String stateType) {
        try {
            FileWriter writer = new FileWriter(CSV_FILE_PATH, true);
            
            // Write headers if this is the initial state
            if (stateType.equals("Initial")) {
                writer.append("State,Player,Score,Cards\n");
            }
            
            // Write each player's state
            for (Player player : players) {
                writer.append(stateType).append(",");
                writer.append(player.getName()).append(",");
                writer.append(String.valueOf(player.getScore())).append(",");
                writer.append(player.getHand().toString()).append("\n");
            }
            
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
