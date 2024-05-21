import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartMenu extends JPanel {
    private JLabel titleLabel;
    private JButton playButton;
    private JButton setMinosButton;
    private ArrayList<JLabel> letterLabels;
    private BufferedImage backgroundImage;
    private PlayManager playManager;

    private StartMenuListener listener;
    public void setStartMenuListener(StartMenuListener listener) {
        this.listener = listener;
    }
    public StartMenu(int panelWidth, int panelHeight) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setBackground(Color.BLACK);

        add(Box.createVerticalStrut(50));

        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\Phuc Vo\\IdeaProjects\\TetrisClone2\\images\\tetris_background.png")); // Replace "background_image.jpg" with your image path
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
            letterLabel.setFont(new Font("Arial", Font.BOLD, 60));
            letterLabel.setForeground(letterColors[i]);
            titlePanel.add(letterLabel);
            letterLabels.add(letterLabel);
        }
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        playButton = new JButton("Play");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setBackground(Color.GREEN);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("SansSerif", Font.PLAIN, 32).deriveFont(Font.BOLD));
        playButton.setPreferredSize(new Dimension(200, 60));
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

        setMinosButton = new JButton("Set Minos");
        setMinosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setMinosButton.setBackground(Color.darkGray);
        setMinosButton.setForeground(Color.WHITE);
        setMinosButton.setFont(new Font("SansSerif", Font.PLAIN, 32).deriveFont(Font.BOLD));
        setMinosButton.setPreferredSize(new Dimension(200, 60));
        setMinosButton.setMaximumSize(setMinosButton.getPreferredSize());
        setMinosButton.setMargin(new Insets(10, 20, 10, 20));
        setMinosButton.addActionListener(e -> {
            String minosString = JOptionPane.showInputDialog(null, "Enter the minos string:");
            if (minosString != null && !minosString.isEmpty()) {
                System.out.println(minosString);
                GamePanel.setMinos = minosString;
                System.out.println(GamePanel.setMinos);
            }
        });

        add(setMinosButton);

        add(Box.createVerticalStrut(60));


        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    public interface StartMenuListener {
        void playButtonClicked();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
