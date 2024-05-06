import java.awt.*;

public class Mino_SQ extends Mino {
    public Mino_SQ() {
        create(Color.yellow);
    }

    public void init(int x, int y) {
        b[0].dx = x;
        b[0].dy = y;
        b[1].dx = b[0].dx;
        b[1].dy = b[0].dy + Block.SIZE;
        b[2].dx = b[0].dx + Block.SIZE;
        b[2].dy = b[0].dy;
        b[3].dx = b[0].dx + Block.SIZE;
        b[3].dy = b[0].dy + Block.SIZE;
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
}
