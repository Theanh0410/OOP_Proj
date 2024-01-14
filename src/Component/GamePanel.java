package Component;


import Objective.Bullet;
import Objective.Effect;
import Objective.Player;
import Objective.Rocket;
import Sound.Sound;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import javax.swing.JComponent;


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
    private List<Effect> boomEff;
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
        initGame();
        initKeyboard();
        initBullets();
        thread.start();
    }

    private void addRocket() {
        Random ran = new Random();
        int locaY1 = ran.nextInt(height - 50) + 25;
        Rocket rocket1 = new Rocket();
        rocket1.changeLocation(0, locaY1);
        rocket1.changeAngle(0);
        rockets.add(rocket1);
        int locaY2 = ran.nextInt(height - 25);
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locaY2);
        rocket2.changeAngle(180);
        rockets.add(rocket2);
    }

    private void initGame() {
        sound = new Sound();
        player = new Player();
        player.changeLocation(150, 150);
        rockets = new ArrayList<>();
        boomEff = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start){
                    addRocket();
                    sleep(4000)
                }
            }
        }).start();
    }

    private void resetGame() {
        score = 0;
        rockets.clear();
        bullets.clear();
        player.changeLocation(150, 150);
        player.reset();
    }

    private void initKeyboard() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_accelerate(true);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_shoot(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    key.setKey_accelerate(false);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_shoot(false);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    if (player.isAlive()) {
                        float angle = player.getAngle();
                        if (key.isKey_right()) {
                            angle += 0.5f;
                        }
                        if (key.isKey_left()) {
                            angle -= 0.5f;
                        }

                        if (key.isKey_shoot()) {
                            if (shootTime == 0) {

                            }
                        }
                    }
                }
            }
        })
    }
    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
}
