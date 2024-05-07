import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            pausePressed = !pausePressed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
