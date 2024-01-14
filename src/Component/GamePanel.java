package Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import javax.swing.JComponent;
import Objective.Bullet;
import Objective.Effect;
import Objective.Player;
import Objective.Rocket;
import Sound.Sound;

public class GamePanel extends JComponent {
    private Graphics2D g2;
    private BufferedImage image;
    private Thread thread;
    private Key key;
    private int width;
    private int height;
    private int shootTime;
    private boolean start = true;

    //FPS
    private final int FPS = 60;
    private final int target_Dur = 1000000000 / 60;

    //Object
    private Sound sound;
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private int score = 0;

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable(){
            @Override
            public void run() {
                while (start) {
                    long startTime = System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time = System.nanoTime() - startTime;
                    if (time < target_Dur) {
                        long sleep = (target_Dur - time) / 1000000;
                        sleep(sleep);
                    }
                }
            }
        });
        initObjectGame();
        initKeyboard();
        initBullets();
        thread.start();
    }


}
