import java.util.HashMap;
import java.util.Map;

public class LeastUsedStrategy implements Strategy {
    private Map<String, Integer> playerMoveCounts;

    public LeastUsedStrategy(Map<String, Integer> playerMoveCounts) {
        this.playerMoveCounts = playerMoveCounts;
    }

    @Override
    public String determineMove() {
        // Determine the least-used move by the player
        String leastUsedMove = playerMoveCounts.entrySet().stream()
                .min(Map.Entry.comparingByValue()) // Find the least-used move
                .map(Map.Entry::getKey)
                .orElse("Rock"); // Default if no move exists

        // Return the move that beats the least-used move
        return getWinningMove(leastUsedMove);
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
