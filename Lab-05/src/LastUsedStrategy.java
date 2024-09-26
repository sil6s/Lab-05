public class LastUsedStrategy implements Strategy {
    private String lastPlayerMove;

    public LastUsedStrategy(String lastPlayerMove) {
        this.lastPlayerMove = lastPlayerMove;
    }

    @Override
    public String determineMove() {
        return getWinningMove(lastPlayerMove);
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
