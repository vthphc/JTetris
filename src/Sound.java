import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private static final String RES_FOLDER = "res/";
    private static final Map<String, File> soundFiles = new HashMap<>();
    private static final Map<String, Clip> clips = new HashMap<>();

    static {
        loadSound("music", "tetris_theme_piano.wav");
        loadSound("rotate", "rotation.wav");
        loadSound("touchdown", "touch floor.wav");
        loadSound("line", "delete line.wav");
        loadSound("gameover", "gameover.wav");
    }

    private static void loadSound(String name, String filename) {
        File file = new File(RES_FOLDER + filename);
        soundFiles.put(name, file);
    }

    public static void playSound(String name) {
        File soundFile = soundFiles.get(name);
        if (soundFile == null) {
            System.err.println("Sound file '" + name + "' not found!");
            return;
        }

        Clip clip = clips.get(name);
        if (clip == null) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clips.put(name, clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
                return;
            }
        }

        clip.setFramePosition(0);
        clip.start();
    }

    public static void stopSound(String name) {
        Clip clip = clips.get(name);
        if (clip != null) {
            clip.stop();
        }
    }
}