import java.util.Random;

public class CheatStrategy implements Strategy {
    private String lastPlayerMove;
    private Random random = new Random();

    public CheatStrategy(String lastPlayerMove) {
        this.lastPlayerMove = lastPlayerMove;
    }

    @Override
    public String determineMove() {
        // 10% chance to cheat
        if (random.nextInt(100) < 10) {
            return getWinningMove(lastPlayerMove); // Cheating, picking the winning move
        }
        // 90% chance to pick randomly
        return new RandomStrategy().determineMove();
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
