import java.awt.*;

public class Mino {
    public Block b[] = new Block[4];
    public Block temp[] = new Block[4];

    public int direction = 1;

    int autoDropCount = 0;

    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            temp[i] = new Block(c);
        }
    }
    public void init(int x, int y) {

    }
    public void updateXY(int direction) {
        this.direction = direction;
        for (int i = 0; i < 4; i++) {
            b[i].dx = temp[i].dx;
            b[i].dy = temp[i].dy;
        }
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void update() {
        if(KeyHandler.upPressed) {
            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
        }
        if(KeyHandler.downPressed) {
            for (int i = 0; i < 4; i++) {
                b[i].dy += Block.SIZE;
            }
            KeyHandler.downPressed = false;
            autoDropCount = 0;
        }
        if(KeyHandler.leftPressed) {
            for (int i = 0; i < 4; i++) {
                b[i].dx -= Block.SIZE;
                KeyHandler.leftPressed = false;
            }
        }
        if(KeyHandler.rightPressed) {
            for (int i = 0; i < 4; i++) {
                b[i].dx += Block.SIZE;
                KeyHandler.rightPressed = false;
            }
        }

        autoDropCount++;
        if(autoDropCount == PlayManager.dropInterval) {
            for(int i = 0; i < 4; i++) {
                b[i].dy += Block.SIZE;
            }
            autoDropCount = 0;
        }
    }
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].dx + margin, b[0].dy + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(b[1].dx + margin, b[1].dy + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(b[2].dx + margin, b[2].dy + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
        g2.fillRect(b[3].dx + margin, b[3].dy + margin, Block.SIZE - (margin*2), Block.SIZE - (margin*2));
    }
}
