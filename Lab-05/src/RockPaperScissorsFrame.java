import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RockPaperScissorsFrame extends JFrame {
    private JComboBox<String> strategyComboBox;
    private JTextArea gameResultsArea;
    private JButton rockButton, paperButton, scissorsButton, quitButton;
    private JLabel playerWinsLabel, computerWinsLabel, tiesLabel;
    private int playerWins, computerWins, ties;
    private Strategy computerStrategy;
    private Map<String, Integer> playerMoveCounts;
    private String lastPlayerMove;

    public RockPaperScissorsFrame() {
        setTitle("Silas's Rock Paper Scissors");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeGame();
        createUI();
        setComputerStrategy("Random");
    }

    private void initializeGame() {
        playerMoveCounts = new HashMap<>();
        playerMoveCounts.put("Rock", 0);
        playerMoveCounts.put("Paper", 0);
        playerMoveCounts.put("Scissors", 0);
        lastPlayerMove = null;
    }

    private void createUI() {
        setLayout(new BorderLayout());

        add(createControlPanel(), BorderLayout.NORTH);
        add(createMainButtonPanel(), BorderLayout.CENTER);
        add(createScrollPane(), BorderLayout.SOUTH);
        add(createStatsPanel(), BorderLayout.EAST);
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        String[] strategies = {"Random", "Least Used", "Most Used", "Last Used", "Cheat"};
        strategyComboBox = new JComboBox<>(strategies);
        strategyComboBox.addActionListener(e -> setComputerStrategy((String) strategyComboBox.getSelectedItem()));

        controlPanel.add(new JLabel("Select Strategy:"));
        controlPanel.add(strategyComboBox);
        return controlPanel;
    }

    private JPanel createMainButtonPanel() {
        JPanel mainButtonPanel = new JPanel(new BorderLayout());
        mainButtonPanel.setBorder(BorderFactory.createTitledBorder("Make Your Move"));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorsButton = new JButton("Scissors");

        buttonPanel.add(createButtonPanel("/resources/rock.png", rockButton));
        buttonPanel.add(createButtonPanel("/resources/paper.png", paperButton));
        buttonPanel.add(createButtonPanel("/resources/scissors.png", scissorsButton));

        mainButtonPanel.add(buttonPanel, BorderLayout.CENTER);
        mainButtonPanel.add(createQuitPanel(), BorderLayout.SOUTH);

        rockButton.addActionListener(e -> playerMove("Rock"));
        paperButton.addActionListener(e -> playerMove("Paper"));
        scissorsButton.addActionListener(e -> playerMove("Scissors"));

        return mainButtonPanel;
    }

    private JPanel createButtonPanel(String iconPath, JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(iconLabel);
            panel.add(Box.createVerticalStrut(5)); // Add some space between icon and button
        } else {
            System.err.println("Failed to load image: " + iconPath);
        }

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(button);

        return panel;
    }

    private JPanel createQuitPanel() {
        JPanel quitPanel = new JPanel();
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        quitPanel.add(quitButton);
        quitPanel.setBorder(BorderFactory.createTitledBorder("Quit"));
        return quitPanel;
    }

    private JScrollPane createScrollPane() {
        gameResultsArea = new JTextArea(10, 50);
        gameResultsArea.setEditable(false);
        return new JScrollPane(gameResultsArea);
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Game Statistics"));
        statsPanel.setBackground(new Color(230, 230, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        playerWinsLabel = new JLabel("Player Wins: 0");
        computerWinsLabel = new JLabel("Computer Wins: 0");
        tiesLabel = new JLabel("Ties: 0");

        gbc.gridy = 0;
        statsPanel.add(playerWinsLabel, gbc);
        gbc.gridy = 1;
        statsPanel.add(computerWinsLabel, gbc);
        gbc.gridy = 2;
        statsPanel.add(tiesLabel, gbc);

        return statsPanel;
    }

    private void setComputerStrategy(String selectedStrategy) {
        switch (selectedStrategy) {
            case "Random":
                computerStrategy = new RandomStrategy();
                break;
            case "Least Used":
                computerStrategy = new LeastUsedStrategy(playerMoveCounts);
                break;
            case "Most Used":
                computerStrategy = new MostUsedStrategy(playerMoveCounts);
                break;
            case "Last Used":
                computerStrategy = new LastUsedStrategy(lastPlayerMove);
                break;
            case "Cheat":
                computerStrategy = new CheatStrategy(lastPlayerMove);
                break;
        }
    }

    private void playerMove(String playerMove) {
        playerMoveCounts.put(playerMove, playerMoveCounts.get(playerMove) + 1);
        lastPlayerMove = playerMove;
        String computerMove = computerStrategy.determineMove();
        String result = determineGameResult(playerMove, computerMove);
        updateStats(result);
        appendResultToTextArea(result, strategyComboBox.getSelectedItem().toString());
    }

    private String determineGameResult(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "It's a tie! Both chose " + playerMove;
        }
        switch (playerMove) {
            case "Rock":
                return computerMove.equals("Scissors") ? "Rock breaks Scissors. Player wins!" : "Paper covers Rock. Computer wins!";
            case "Paper":
                return computerMove.equals("Rock") ? "Paper covers Rock. Player wins!" : "Scissors cut Paper. Computer wins!";
            case "Scissors":
                return computerMove.equals("Paper") ? "Scissors cut Paper. Player wins!" : "Rock breaks Scissors. Computer wins!";
            default:
                return "Error in determining result";
        }
    }

    private void appendResultToTextArea(String result, String strategy) {
        gameResultsArea.append(result + " (" + strategy + ")\n");
        gameResultsArea.setCaretPosition(gameResultsArea.getDocument().getLength());
    }

    private void updateStats(String result) {
        if (result.contains("Player wins")) {
            playerWins++;
        } else if (result.contains("Computer wins")) {
            computerWins++;
        } else {
            ties++;
        }
        playerWinsLabel.setText("Player Wins: " + playerWins);
        computerWinsLabel.setText("Computer Wins: " + computerWins);
        tiesLabel.setText("Ties: " + ties);
    }

    public Strategy getComputerStrategy() {
        return computerStrategy;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RockPaperScissorsFrame frame = new RockPaperScissorsFrame();
            frame.setVisible(true);
        });
    }
}