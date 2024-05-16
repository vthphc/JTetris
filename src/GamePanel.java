import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager pm = new PlayManager();
    public static String setMinos;
    public static Sound music = new Sound();
    public static Sound soundEffect = new Sound();

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        requestFocus();
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);
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
        if(!KeyHandler.pausePressed && !pm.isGameOver ) {
            pm.update(setMinos);
        }
        else if (pm.isGameOver) {
            if(KeyHandler.restartPressed) {
                pm = new PlayManager();
                pm.reset();
                KeyHandler.restartPressed = false;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    }
}
