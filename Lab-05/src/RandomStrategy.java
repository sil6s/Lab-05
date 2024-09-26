import java.util.Random;

public class RandomStrategy implements Strategy {
    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};
    private Random random = new Random();

    @Override
    public String determineMove() {
        int index = random.nextInt(MOVES.length);
        return MOVES[index];
    }
}
