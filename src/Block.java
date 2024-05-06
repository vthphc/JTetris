import java.awt.*;

public class Block extends Rectangle {
    public int dx;
    public int dy;
    public static final int SIZE = 30;
    public Color c;

    public Block(Color c) {
        this.c = c;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.fillRect(dx, dy, SIZE, SIZE);
    }
}
