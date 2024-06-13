import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm;
    public static Sound soundEffect = new Sound();
    JFrame parentFrame;
    private Font customFont;

    public GamePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        requestFocus();
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
    }

    public void setCustomFont(Font font) {
        this.customFont = font;
    }

    public void initializePlayManager() {
        pm = new PlayManager(parentFrame, customFont);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (pm == null) {
            initializePlayManager();
        }

        if (!KeyHandler.pausePressed && !pm.isGameOver) {
            pm.update();
        } else if (pm.isGameOver) {
            if (KeyHandler.restartPressed) {
                pm.reset();
                KeyHandler.restartPressed = false;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pm != null) {
            Graphics2D g2 = (Graphics2D) g;
            pm.draw(g2);
        }
    }
}
