package Sound;

import javax.sound.sampled.*;
import java.net.URL;
import java.io.IOException;

public class Sound {
    private final URL shoot;
    private final URL hit;
    private final URL destroy;

    public Sound() {
        this.shoot = this.getClass().getClassLoader().getResource("src/Sound/shoot.wav");
        this.hit = this.getClass().getClassLoader().getResource("src/Sound/hit.wav");
        this.destroy = this.getClass().getClassLoader().getResource("src/Sound/destroy.wav");
    }

    public void soundShoot() {
        play(shoot);
    }

    public void soundHit() {
        play(hit);
    }

    public void soundDestroy() {
        play(destroy);
    }

    private void play(URL url) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });
            audioIn.close();
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println(e);
        }
    }
}
