import java.awt.*;

public class Mino {
    public Block b[] = new Block[4];
    public Block temp[] = new Block[4];
    public int direction = 1;
    int autoDropCount = 0;
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean isStopped = false;
    public boolean deactivating;
    int deactivatingCount = 0;

    public void create(Color c) {
        for (int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            temp[i] = new Block(c);
        }
    }
    public void init(int x, int y) {

    }
    public void updateXY(int direction) {
        checkRotationCollision();
        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            for (int i = 0; i < 4; i++) {
                b[i].dx = temp[i].dx;
                b[i].dy = temp[i].dy;
            }
        }
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkBlockCollision();

        for (int i = 0; i < 4; i++) {
            if(b[i].dx == PlayManager.left_x) {
                leftCollision = true;
            }
            if(b[i].dx + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;
            }
            if(b[i].dy + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }
    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkBlockCollision();

        for (int i = 0; i < 4; i++) {
            if(temp[i].dx < PlayManager.left_x) {
                leftCollision = true;
            }
            if(temp[i].dx + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
            if(temp[i].dy + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }
    public void checkBlockCollision() {
        for (int i = 0; i < PlayManager.blocks.size(); i++) {
            int targetX = PlayManager.blocks.get(i).dx;
            int targetY = PlayManager.blocks.get(i).dy;
            for (int j = 0; j < 4; j++) {
                if(b[j].dx == targetX && b[j].dy + Block.SIZE == targetY) {
                    bottomCollision = true;
                }
                if(b[j].dx == targetX && b[j].dy == targetY + Block.SIZE) {
                    bottomCollision = true;
                }
                if(b[j].dx == targetX + Block.SIZE && b[j].dy == targetY) {
                    rightCollision = true;
                }
                if(b[j].dx == targetX - Block.SIZE && b[j].dy == targetY) {
                    leftCollision = true;
                }
            }
        }
    }
    public void update() {
        if(deactivating) {
            deactivate();
        }
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
            GamePanel.soundEffect.playSound("rotate");
        }
        checkMovementCollision();
        if(KeyHandler.downPressed) {
            if(!bottomCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].dy += Block.SIZE;
                }
                KeyHandler.downPressed = false;
                autoDropCount = 0;
            }
        }
        if(KeyHandler.leftPressed) {
            if(!leftCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].dx -= Block.SIZE;
                }
                KeyHandler.leftPressed = false;
            }
        }
        if(KeyHandler.rightPressed) {
            if(!rightCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].dx += Block.SIZE;
                }
                KeyHandler.rightPressed = false;
            }
        }

        if(bottomCollision) {
            GamePanel.soundEffect.playSound("touchdown");
            deactivating = true;
        }
        else {
            autoDropCount++;
            if(autoDropCount == PlayManager.dropInterval) {
                for(int i = 0; i < 4; i++) {
                    b[i].dy += Block.SIZE;
                }
                autoDropCount = 0;
            }
        }
    }
    private void deactivate() {
        deactivatingCount++;
        if(deactivatingCount == 10) {
            deactivatingCount = 0;
            checkMovementCollision();
            if(bottomCollision) {
                isStopped = true;
            }
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
