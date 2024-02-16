import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 * This class represents a simple graphical user interface for playing the Rock Paper Scissors game.
 * It allows a player to choose between Rock, Paper, or Scissors and play against the computer,
 * which makes a random choice. The game keeps track of the number of player wins, computer wins,
 * and ties, and displays this information along with the results of each round.
 */
public class RockPaperScissorsFrame extends JFrame
{
    private JTextArea resultsArea;
    private JTextField playerWinsField;
    private JTextField computerWinsField;
    private JTextField tiesField;
    private int playerWins = 0;
    private int computerWins = 0;
    private int ties = 0;

    /**
     * Constructs a RockPaperScissorsFrame and initializes the game interface.
     */
    public RockPaperScissorsFrame()
    {
        setTitle("Rock Paper Scissors Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createButtonsPanel(), BorderLayout.NORTH);
        add(createResultsPanel(), BorderLayout.CENTER);
        add(createStatsPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Scales an image icon to the specified width and height.
     *
     * @param path The path of the image file.
     * @param width The desired width.
     * @param height The desired height.
     * @return An ImageIcon that has been scaled to the desired size.
     */
    private ImageIcon scaleImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a panel containing buttons for the player to choose Rock, Paper, or Scissors, and a Quit button.
     *
     * @return A JPanel containing the game buttons.
     */
    private JPanel createButtonsPanel()
    {
        JPanel panel = new JPanel();
        panel.setBorder((BorderFactory.createTitledBorder("Select Move")));

        JButton rockButton = new JButton("Rock", scaleImage("/fist.png", 70, 70)); // Adjust width and height as needed
        JButton paperButton = new JButton("Paper", scaleImage("/hand-paper.png", 70, 70));
        JButton scissorsButton = new JButton("Scissors", scaleImage("/scissors.png", 70, 70));
        JButton quitButton = new JButton("Quit", scaleImage("/error.png", 70, 70));

        rockButton.addActionListener(e -> playGame("Rock"));
        paperButton.addActionListener(e -> playGame("Paper"));
        scissorsButton.addActionListener(e -> playGame("Scissors"));
        quitButton.addActionListener(e -> System.exit(0));

        panel.add(rockButton);
        panel.add(paperButton);
        panel.add(scissorsButton);
        panel.add(quitButton);

        return panel;
    }

    /**
     * Creates a panel displaying the current statistics of the game, including player wins, computer wins, and ties.
     *
     * @return A JPanel displaying the game statistics.
     */
    private  JPanel createStatsPanel()
    {
        JPanel statsPanel = new JPanel();
        statsPanel.add(new JLabel("Player Wins:"));
        playerWinsField = new JTextField("0", 5);
        playerWinsField.setEditable(false);
        statsPanel.add(playerWinsField);

        statsPanel.add(new JLabel("Computer Wins:"));
        computerWinsField = new JTextField("0", 5);
        computerWinsField.setEditable(false);
        statsPanel.add(computerWinsField);

        statsPanel.add(new JLabel("Ties:"));
        tiesField = new JTextField("0", 5);
        tiesField.setEditable(false);
        statsPanel.add(tiesField);

        return statsPanel;
    }

    /**
     * Creates a scrollable text area for displaying the results of each game round.
     *
     * @return A JScrollPane containing the results text area.
     */
    private JScrollPane createResultsPanel()
    {
        resultsArea = new JTextArea(10, 30);
        resultsArea.setFont(new Font("Arial", Font.PLAIN, 20));
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        return new JScrollPane(scrollPane);
    }

    /**
     * Plays a single game round with the player's choice, randomly selects the computer's choice,
     * determines the winner, and updates the game statistics and results.
     *
     * @param playerChoice The player's choice of "Rock", "Paper", or "Scissors".
     */
    private void playGame(String playerChoice)
    {
        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[new Random().nextInt(choices.length)];
        String result;

        if (playerChoice.equals(computerChoice))
        {
            ties++;
            result = "It's a tie!";
        } else if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                  (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                          (playerChoice.equals("Scissors") && computerChoice.equals("Paper")))
        {
            playerWins++;
            result = playerChoice + " breaks " + computerChoice + ". Player wins!";
        } else {
            computerWins ++;
            result = computerChoice + " covers " + playerChoice + ". Computer wins!";
        }

        updateResultsAndStats(result);
    }

    /**
     * Updates the results area and the statistics fields with the latest game result and statistics.
     *
     * @param result The result of the latest game round to be displayed.
     */
    private void updateResultsAndStats(String result)
    {
        resultsArea.append(result + "\n");
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }
}
