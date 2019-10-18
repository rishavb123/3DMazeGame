import java.awt.Graphics;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Trap extends PhaseObject {

    public static int width3d = Application.screenWidth / 3;
    public static int height3d = Application.screenHeight;

    public Trap(int x, int y) {
        super(x, y);
    }

    @Override
    public void put(GameObject obj) {
        if (obj instanceof Explorer) {
            ((Explorer) obj).damage(((Explorer) obj).getMaxHealth() / 10);
            String soundName = "./res/scream.wav";
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        super.put(obj);
    }

    @Override
    public void draw(Graphics g) {
        int x = location.getX();
        int y = location.getY();
        g.setColor(Color.RED);
        g.fillRect(x * Wall.width, y * Wall.height, Wall.width, Wall.height);
        g.setColor(Color.WHITE);
        g.drawRect(x * Wall.width, y * Wall.height, Wall.width, Wall.height);
        super.draw(g);
    }

}