import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
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
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    public PlayManager() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = WIDTH + left_x;
        top_y = 50;
        bottom_y = HEIGHT + top_y;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXT_MINO_X = right_x + 190;
        NEXT_MINO_Y = top_y + 480;

        if (GamePanel.setMinos == null) {
            currentMino = pickRandomMino();
            currentMino.init(MINO_START_X, MINO_START_Y);
            nextMino = pickRandomMino();
            nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);
        }
        else {
            for (int i = 0; i < GamePanel.setMinos.length(); i += 2) {
                Mino mino = getSetMinos(GamePanel.setMinos.substring(i, i + 2));
                if (currentMino == null) {
                    currentMino = mino;
                    currentMino.init(MINO_START_X, MINO_START_Y);
                }
                else {
                    nextMino = mino;
                    nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);
                }
            }
        }
    }

    private Mino getSetMinos(String setMinos) {
        Mino mino = null;
        switch (setMinos) {
            case "BR":
                mino = new Mino_BR();
                break;
            case "L1":
                mino = new Mino_L1();
                break;
            case "L2":
                mino = new Mino_L2();
                break;
            case "SQ":
                mino = new Mino_SQ();
                break;
            case "T0":
                mino = new Mino_T0();
                break;
            case "Z1":
                mino = new Mino_Z1();
                break;
            case "Z2":
                mino = new Mino_Z2();
                break;
        }
        return mino;
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
                    if(b.dx == x && b.dy == y) {
                        count++;
                    }
                }
            }
            if(count == 12) {
                effectCounterOn = true;
                effectY.add(y);

                GamePanel.soundEffect.playSound("line");
                score += 100;

                for (int i = 0; i < blocks.size(); i++) {
                    if(blocks.get(i).dy == y) {
                        blocks.remove(i);
                        i--;
                    }
                }

                for (int i = 0; i < blocks.size(); i++) {
                    if(blocks.get(i).dy < y) {
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

    public void update(String setMinos) {
        if (setMinos == null) {
            if(currentMino.isStopped) {
                for (int i = 0; i < 4; i++) {
                    blocks.add(currentMino.b[i]);
                }

                if(currentMino.b[0].dx == MINO_START_X && currentMino.b[0].dy == MINO_START_Y) {
                    isGameOver = true;
                    GamePanel.soundEffect.playSound("gameover");
                }

                currentMino.deactivating = false;

                currentMino = nextMino;
                currentMino.init(MINO_START_X, MINO_START_Y);
                nextMino = pickRandomMino();
                nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);

                checkDelete();
            }
            else {
                currentMino.update();
            }
        }
        else {
            if(currentMino.isStopped) {
                for (int i = 0; i < 4; i++) {
                    blocks.add(currentMino.b[i]);
                }

                if(currentMino.b[0].dx == MINO_START_X && currentMino.b[0].dy == MINO_START_Y) {
                    isGameOver = true;
                    GamePanel.soundEffect.playSound("gameover");
                }

                currentMino.deactivating = false;

                for (int i = 0; i < setMinos.length(); i += 2) {
                    Mino mino = getSetMinos(setMinos.substring(i, i + 2));
                    currentMino = mino;
                    currentMino.init(MINO_START_X, MINO_START_Y);
                }
                nextMino = pickRandomMino();
                nextMino.init(NEXT_MINO_X, NEXT_MINO_Y);

                checkDelete();
            }
            else {
                currentMino.update();
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        int nextAreaX = right_x + 50;
        int nextAreaY = top_y + 50;
        int nextAreaWidth = 150;
        int nextAreaHeight = 150;
        g2.drawRect(nextAreaX, nextAreaY, nextAreaWidth, nextAreaHeight);

        g2.setFont(new Font("Arial", Font.PLAIN, 30).deriveFont(Font.BOLD));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("NEXT", nextAreaX + 35, nextAreaY - 15);

        int scoreAreaX = right_x + 50;
        int scoreAreaY = nextAreaY + nextAreaHeight + 50;
        g2.drawString("Score: " + score, scoreAreaX, scoreAreaY);

        if(currentMino != null) {
            currentMino.draw(g2);
        }

        if (nextMino != null) {
            int minoSize = Block.SIZE * 4; // Assuming the mino is 4x4 blocks
            int nextMinoX = nextAreaX + (nextAreaWidth - minoSize) / 2 + 30;
            int nextMinoY = nextAreaY + (nextAreaHeight - minoSize) / 2 + 30;
            nextMino.init(nextMinoX, nextMinoY);
            nextMino.draw(g2);
        }

        for (Block b : blocks) {
            b.draw(g2);
        }

        if(effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.white);
            int margin = 2;
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x + margin, effectY.get(i) + margin, WIDTH - (margin*2), Block.SIZE - (margin*2));
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30.0f).deriveFont(Font.BOLD));
        FontMetrics fontMetrics = g2.getFontMetrics();

        String gameOverText = "GAME OVER";
        int gameOverStringWidth = fontMetrics.stringWidth(gameOverText);
        int xGameOver = (GamePanel.WIDTH - gameOverStringWidth) / 2;
        int yGameOver = GamePanel.HEIGHT / 2;

        String restartText = "PRESS R TO RESTART";
        int restartStringWidth = fontMetrics.stringWidth(restartText);
        int xRestart = (GamePanel.WIDTH - restartStringWidth) / 2;
        int yRestart = yGameOver + 50;

        String pauseText = "PAUSED";
        int pauseStringWidth = fontMetrics.stringWidth(pauseText);
        int xPause = (GamePanel.WIDTH - pauseStringWidth) / 2;
        int yPause = GamePanel.HEIGHT / 2;

        if(isGameOver) {
            g2.drawString(gameOverText, xGameOver, yGameOver);
            g2.drawString(restartText, xRestart, yRestart);

        }
        else if(KeyHandler.pausePressed) {
            g2.drawString(pauseText, xPause, yPause);
        }
    }
}
