import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartMenu extends JPanel {
    private JLabel titleLabel;
    private JButton playButton;
    private JButton leaderBoardButton;
    private ArrayList<JLabel> letterLabels;
    private BufferedImage backgroundImage;
    private Font customFont;
    private StartMenuListener listener;

    public StartMenu(int panelWidth, int panelHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setBackground(Color.BLACK);

        add(Box.createVerticalStrut(50));

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Phuc Vo\\IdeaProjects\\TetrisClone2\\images\\tetris_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel titlePanel = new JPanel();
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        add(titlePanel);

        letterLabels = new ArrayList<>();
        Color[] letterColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
        String titleText = "TETRIS";
        for (int i = 0; i < titleText.length(); i++) {
            JLabel letterLabel = new JLabel(titleText.substring(i, i + 1));
            if (customFont != null) {
                letterLabel.setFont(customFont.deriveFont(Font.BOLD, 60));
            } else {
                letterLabel.setFont(new Font("Arial", Font.BOLD, 60));
            }
            letterLabel.setForeground(letterColors[i]);
            titlePanel.add(letterLabel);
            letterLabels.add(letterLabel);
        }
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        playButton = new JButton("Play");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(customFont != null ? customFont.deriveFont(Font.BOLD, 32) : new Font("SansSerif", Font.PLAIN, 32).deriveFont(Font.BOLD));
        playButton.setPreferredSize(new Dimension(280, 60));
        playButton.setMaximumSize(playButton.getPreferredSize());
        playButton.setMargin(new Insets(10, 20, 10, 20));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.playButtonClicked();
                }
            }
        });
        add(playButton);

        add(Box.createVerticalStrut(10));

        leaderBoardButton = new JButton("Leaderboard");
        leaderBoardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderBoardButton.setBackground(Color.DARK_GRAY);
        leaderBoardButton.setForeground(Color.WHITE);
        leaderBoardButton.setFont(customFont != null ? customFont.deriveFont(Font.BOLD, 32) : new Font("SansSerif", Font.PLAIN, 32).deriveFont(Font.BOLD));
        leaderBoardButton.setPreferredSize(new Dimension(280, 60));
        leaderBoardButton.setMaximumSize(leaderBoardButton.getPreferredSize());
        leaderBoardButton.setMargin(new Insets(10, 20, 10, 20));
        leaderBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.leaderBoardButtonClicked();
                }
            }
        });
        add(leaderBoardButton);

        add(Box.createVerticalStrut(60));

        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public void setCustomFont(Font font) {
        this.customFont = font;
        for (JLabel letterLabel : letterLabels) {
            letterLabel.setFont(customFont.deriveFont(Font.BOLD, 60));
        }
        playButton.setFont(customFont.deriveFont(Font.PLAIN, 24));
        leaderBoardButton.setFont(customFont.deriveFont(Font.PLAIN, 20));
    }

    public void setStartMenuListener(StartMenuListener listener) {
        this.listener = listener;
    }

    public interface StartMenuListener {
        void playButtonClicked();
        void leaderBoardButtonClicked();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
