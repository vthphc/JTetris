import java.awt.*;

public class Mino_T0 extends Mino {
    public Mino_T0() {
        create(Color.magenta);
    }

    public void init(int x, int y) {
        b[0].dx = x;
        b[0].dy = y;
        b[1].dx = b[0].dx - Block.SIZE;
        b[1].dy = b[0].dy;
        b[2].dx = b[0].dx + Block.SIZE;
        b[2].dy = b[0].dy;
        b[3].dx = b[0].dx;
        b[3].dy = b[0].dy + Block.SIZE;
    }
    public void getDirection1() {
        temp[0].dx = b[0].dx;
        temp[0].dy = b[0].dy;
        temp[1].dx = b[0].dx - Block.SIZE;
        temp[1].dy = b[0].dy;
        temp[2].dx = b[0].dx + Block.SIZE;
        temp[2].dy = b[0].dy;
        temp[3].dx = b[0].dx;
        temp[3].dy = b[0].dy + Block.SIZE;
        updateXY(1);
    }
    public void getDirection2() {
        temp[0].dx = b[0].dx;
        temp[0].dy = b[0].dy;
        temp[1].dx = b[0].dx;
        temp[1].dy = b[0].dy - Block.SIZE;
        temp[2].dx = b[0].dx;
        temp[2].dy = b[0].dy + Block.SIZE;
        temp[3].dx = b[0].dx - Block.SIZE;
        temp[3].dy = b[0].dy;
        updateXY(2);
    }
    public void getDirection3() {
        temp[0].dx = b[0].dx;
        temp[0].dy = b[0].dy;
        temp[1].dx = b[0].dx - Block.SIZE;
        temp[1].dy = b[0].dy;
        temp[2].dx = b[0].dx + Block.SIZE;
        temp[2].dy = b[0].dy;
        temp[3].dx = b[0].dx;
        temp[3].dy = b[0].dy - Block.SIZE;
        updateXY(3);
    }
    public void getDirection4() {
        temp[0].dx = b[0].dx;
        temp[0].dy = b[0].dy;
        temp[1].dx = b[0].dx;
        temp[1].dy = b[0].dy - Block.SIZE;
        temp[2].dx = b[0].dx;
        temp[2].dy = b[0].dy + Block.SIZE;
        temp[3].dx = b[0].dx + Block.SIZE;
        temp[3].dy = b[0].dy;
        updateXY(4);
    }
}
