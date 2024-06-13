import javax.swing.*;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tetris");
        StartMenu sm = new StartMenu(1280, 720);
        GamePanel gp = new GamePanel(window);

        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PressStart2P-Regular.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont);

            sm.setCustomFont(pixelFont);
            gp.setCustomFont(pixelFont);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sm.setStartMenuListener(new StartMenu.StartMenuListener() {
            public void playButtonClicked() {
                window.remove(sm);
                window.add(gp);
                window.revalidate();
                gp.launchGame();
                gp.requestFocusInWindow();
            }

            public void leaderBoardButtonClicked() {
                LeaderBoardDialog.showLeaderBoard(window);
            }
        });

        window.add(sm);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
