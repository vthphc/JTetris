import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Tetris");
        GamePanel gp = new GamePanel();
        StartMenu sm = new StartMenu(1280, 720);
        sm.setStartMenuListener(new StartMenu.StartMenuListener() {
            public void playButtonClicked() {
                window.remove(sm);
                window.add(gp);
                window.revalidate();
                gp.launchGame();
                gp.requestFocusInWindow();
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