import java.util.HashMap;
import java.util.Map;

public class MostUsedStrategy implements Strategy {
    private Map<String, Integer> playerMoveCounts;

    public MostUsedStrategy(Map<String, Integer> playerMoveCounts) {
        this.playerMoveCounts = playerMoveCounts;
    }

    @Override
    public String determineMove() {
        // Determine the most-used move by the player
        String mostUsedMove = playerMoveCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Find the most-used move
                .map(Map.Entry::getKey)
                .orElse("Rock"); // Default if no move exists

        // Return the move that beats the most-used move
        return getWinningMove(mostUsedMove);
    }

    private String getWinningMove(String move) {
        switch (move) {
            case "Rock": return "Paper"; // Paper beats Rock
            case "Paper": return "Scissors"; // Scissors beats Paper
            case "Scissors": return "Rock"; // Rock beats Scissors
            default: return "Rock"; // Fallback, should never happen
        }
    }
}
