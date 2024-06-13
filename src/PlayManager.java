import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class PlayManager {
    private JFrame parentFrame;
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static int dropInterval = 60;
    public boolean isGameOver;
    private int score = 0;
    private int level = 1;
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();
    private ArrayList<Block> borderBlocks;
    private Font customFont;
    private JButton backButton;

    public PlayManager(JFrame parentFrame, Font customFont) {
        this.parentFrame = parentFrame;
        this.customFont = customFont;

        left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
        right_x = WIDTH + left_x;
        top_y = 50;
        bottom_y = HEIGHT + top_y;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXT_MINO_X = right_x + 190;
        NEXT_MINO_Y = top_y + 480;

        currentMino = pickRandomMino();
        currentMino.init(MINO_START_X, MINO_START_Y);
        nextMino = pickRandomMino();
        nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);

        initializeBorderBlocks();
        initializeBackButton();
    }

    private void initializeBorderBlocks() {
        borderBlocks = new ArrayList<>();

        for (int x = left_x - Block.SIZE; x <= right_x; x += Block.SIZE) {
            Block block = new Block(Color.GRAY);
            block.dx = x;
            block.dy = top_y - Block.SIZE;
            borderBlocks.add(block);
        }

        for (int x = left_x - Block.SIZE; x <= right_x; x += Block.SIZE) {
            Block block = new Block(Color.GRAY);
            block.dx = x;
            block.dy = bottom_y;
            borderBlocks.add(block);
        }

        for (int y = top_y - Block.SIZE; y <= bottom_y; y += Block.SIZE) {
            Block block = new Block(Color.GRAY);
            block.dx = left_x - Block.SIZE;
            block.dy = y;
            borderBlocks.add(block);
        }

        for (int y = top_y - Block.SIZE; y <= bottom_y; y += Block.SIZE) {
            Block block = new Block(Color.GRAY);
            block.dx = right_x;
            block.dy = y;
            borderBlocks.add(block);
        }
    }

    private void initializeBackButton() {
        backButton = new JButton("Back to Menu");
        backButton.setFont(customFont.deriveFont(16f).deriveFont(Font.BOLD));
        backButton.setBounds(right_x + 50, bottom_y - 10, 250, 40);
        backButton.addActionListener(e -> {
            goToStartMenu();
        });
        GamePanel gamePanel = (GamePanel) parentFrame.getContentPane().getComponent(0);
        gamePanel.setLayout(null);
        gamePanel.add(backButton);
    }

    private void goToStartMenu() {
        isGameOver = true;
        parentFrame.getContentPane().removeAll();
        StartMenu startMenu = new StartMenu(parentFrame.getWidth(), parentFrame.getHeight());
        startMenu.setCustomFont(customFont);
        parentFrame.add(startMenu);
        parentFrame.revalidate();
        parentFrame.repaint();

        GamePanel gp = new GamePanel(parentFrame);
        gp.setCustomFont(customFont);
        startMenu.setStartMenuListener(new StartMenu.StartMenuListener() {
            public void playButtonClicked() {
                parentFrame.remove(startMenu);
                parentFrame.add(gp);
                parentFrame.revalidate();
                gp.launchGame();
                gp.requestFocusInWindow();
            }

            public void leaderBoardButtonClicked() {
                LeaderBoardDialog.showLeaderBoard(parentFrame);
            }
        });
    }

    private Mino pickRandomMino() {
        Mino mino = null;
        int random = new Random().nextInt(7);
        switch (random) {
            case 0:
                mino = new Mino_BR();
                break;
            case 1:
                mino = new Mino_L1();
                break;
            case 2:
                mino = new Mino_L2();
                break;
            case 3:
                mino = new Mino_SQ();
                break;
            case 4:
                mino = new Mino_T0();
                break;
            case 5:
                mino = new Mino_Z1();
                break;
            case 6:
                mino = new Mino_Z2();
                break;
        }
        return mino;
    }

    private void checkDelete() {
        for (int y = top_y; y < bottom_y; y += Block.SIZE) {
            int count = 0;
            for (int x = left_x; x < right_x; x += Block.SIZE) {
                for (Block b : blocks) {
                    if (b.dx == x && b.dy == y) {
                        count++;
                    }
                }
            }
            if (count == 12) {
                effectCounterOn = true;
                effectY.add(y);

                GamePanel.soundEffect.playSound("line");
                score += 100;

                if (score % 500 == 0) {
                    level++;
                    dropInterval -= 10;
                }

                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).dy == y) {
                        blocks.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).dy < y) {
                        blocks.get(i).dy += Block.SIZE;
                    }
                }
                if (top_y == y) {
                    top_y += Block.SIZE;
                }
            }
        }
    }

    public void reset() {
        blocks.clear();
        currentMino = pickRandomMino();
        currentMino.init(MINO_START_X, MINO_START_Y);
        nextMino = pickRandomMino();
        nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);
        score = 0;
        isGameOver = false;
    }

    public void update() {
        if (currentMino.isStopped) {
            for (int i = 0; i < 4; i++) {
                blocks.add(currentMino.b[i]);
            }

            if (currentMino.b[0].dx == MINO_START_X && currentMino.b[0].dy == MINO_START_Y) {
                isGameOver = true;
                GamePanel.soundEffect.playSound("gameover");
                showGameOverDialog();
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.init(MINO_START_X, MINO_START_Y);
            nextMino = pickRandomMino();
            nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);

            checkDelete();
        } else {
            currentMino.update();
        }
    }

    private void showGameOverDialog() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameOverDialog dialog = new GameOverDialog(parentFrame, score);
                dialog.setVisible(true);
            }
        });
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        drawGameArea(g2);
        drawNextArea(g2);
        drawScore(g2);
        drawMinos(g2);
        drawEffects(g2);
        drawGameOver(g2);
        drawPause(g2);
    }

    private void drawGameArea(Graphics2D g2) {
        for (Block block : borderBlocks) {
            block.draw(g2);
        }
    }

    private void drawNextArea(Graphics2D g2) {
        int nextAreaX = right_x + 50;
        int nextAreaY = top_y + 50;
        int nextAreaWidth = 150;
        int nextAreaHeight = 150;

        g2.setFont(customFont.deriveFont(18f).deriveFont(Font.BOLD));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("Next Mino", nextAreaX + 35, nextAreaY - 15);

        if (nextMino != null) {
            int minoSize = Block.SIZE * 4;
            int nextMinoX = nextAreaX + 60 + (nextAreaWidth - minoSize) / 2;
            int nextMinoY = nextAreaY + 50 + (nextAreaHeight - minoSize) / 2;
            nextMino.init(nextMinoX, nextMinoY);
            nextMino.draw(g2);
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setFont(customFont.deriveFont(24f).deriveFont(Font.BOLD));
        int scoreAreaX = right_x + 50 + 45;
        int scoreAreaY = top_y + 250;
        g2.drawString("Score: " + score, scoreAreaX, scoreAreaY);

        int levelAreaX = right_x + 50 + 45;
        int levelAreaY = top_y + 300;
        g2.drawString("Level: " + level, levelAreaX, levelAreaY);
    }

    private void drawMinos(Graphics2D g2) {
        if (currentMino != null) {
            currentMino.draw(g2);
        }

        for (Block b : blocks) {
            b.draw(g2);
        }
    }

    private void drawEffects(Graphics2D g2) {
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.WHITE);
            int margin = 2;
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x + margin, effectY.get(i) + margin, WIDTH - (margin * 2), Block.SIZE - (margin * 2));
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }
    }

    private void drawGameOver(Graphics2D g2) {
        if (isGameOver) {
            g2.setColor(Color.WHITE);
            g2.setFont(customFont.deriveFont(30.0f).deriveFont(Font.BOLD));
            FontMetrics fontMetrics = g2.getFontMetrics();

            String gameOverText = "GAME OVER";
            int gameOverStringWidth = fontMetrics.stringWidth(gameOverText);
            int xGameOver = (GamePanel.WIDTH - gameOverStringWidth) / 2;
            int yGameOver = GamePanel.HEIGHT / 2;

            String restartText = "PRESS R TO RESTART";
            int restartStringWidth = fontMetrics.stringWidth(restartText);
            int xRestart = (GamePanel.WIDTH - restartStringWidth) / 2;
            int yRestart = yGameOver + 50;

            g2.drawString(gameOverText, xGameOver, yGameOver);
            g2.drawString(restartText, xRestart, yRestart);
        }
    }

    private void drawPause(Graphics2D g2) {
        if (KeyHandler.pausePressed) {
            g2.setColor(Color.WHITE);
            g2.setFont(customFont.deriveFont(30.0f).deriveFont(Font.BOLD));
            FontMetrics fontMetrics = g2.getFontMetrics();

            String pauseText = "PAUSED";
            int pauseStringWidth = fontMetrics.stringWidth(pauseText);
            int xPause = (GamePanel.WIDTH - pauseStringWidth) / 2;
            int yPause = GamePanel.HEIGHT / 2;

            g2.drawString(pauseText, xPause, yPause);
        }
    }
}
