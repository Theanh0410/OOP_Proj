package Component;

import Objective.Bullet;
import Objective.Effect;
import Objective.Player;
import Objective.Rocket;
import Sound.Sound;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
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
    private final int target_Dur = 1000000000 / FPS;

    //Object
    private Sound sound;
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<Effect> boomEffs;
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
        int locaY2 = ran.nextInt(height - 50) - 25;
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
        boomEffs = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start){
                    addRocket();
                    sleep(4000);
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
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    key.setKey_accelerate(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_shoot(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    key.setKey_accelerate(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_shoot(false);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    float s = 0.5f;
                    if (player.isAlive()) {
                        float angle = player.getAngle();
                        if (key.isKey_right()) {
                            angle += s;
                        }
                        if (key.isKey_left()) {
                            angle -= s;
                        }

                        if (key.isKey_shoot()) {
                            if (shootTime == 0) {
                                bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 10, 3f));

                                sound.soundShoot();
                            }
                            shootTime++;
                            if (shootTime == 15) {
                                shootTime = 0;
                            }
                        } else {
                            shootTime = 0;
                        }

                        if (key.isKey_accelerate()) {
                            player.speedUp();
                        } else {
                            player.speedDown();
                        }

                        player.update();
                        player.changeAngle(angle);
                    } else {
                        if (key.isKey_enter()) {
                            resetGame();
                        }
                    }

                    for (int i = 0; i < rockets.size(); i++) {
                        Rocket rocket = rockets.get(i);
                        if (rocket != null) {
                            rocket.update();
                            if (!rocket.check(width, height)) {
                                rockets.remove(rocket);
                            } else {
                                if (player.isAlive()) {
                                    checkPlayer(rocket);
                                }
                            }
                        }
                    }

                    sleep(5);
                }
            }
        }).start();
    }

    private void initBullets() {
        bullets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.update();
                            checkBullets(bullet);
                            if (!bullet.check(width, height)) {
                                bullets.remove(bullet);
                            }
                        } else {
                            bullets.remove(bullet);
                        }
                    }

                    for (int i = 0; i < boomEffs.size(); i++) {
                        Effect boomEff = boomEffs.get(i);
                        if (boomEff != null) {
                            boomEff.update();
                            if (!boomEff.check()) {
                                boomEffs.remove(boomEff);
                            }
                        } else {
                            boomEffs.remove(boomEff);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }

    private void checkBullets(Bullet bullet) {
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                Area area = new Area((bullet.getShape()));
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    boomEffs.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 3, 10, 60, 0.5f, new Color(230, 230, 230)));

                    if (!rocket.updateHP(bullet.getSize())){
                        score++;
                        rockets.remove(rocket);
                        sound.soundDestroy();
                        double x = rocket.getX() + Rocket.rocket_Size / 2;
                        double y = rocket.getY() + Rocket.rocket_Size / 2;
                        boomEffs.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                        boomEffs.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                    } else {
                        sound.soundHit();
                    }
                    bullets.remove(bullet);
                }
            }
        }
    }

    private void checkPlayer(Rocket rocket) {
        if (rocket != null) {
            Area area = new Area(player.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHP();
                if (!rocket.updateHP(player.getHP())) {
                    rockets.remove(rocket);
                    sound.soundDestroy();
                    double x = rocket.getX() + Rocket.rocket_Size / 2;
                    double y = rocket.getY() + Rocket.rocket_Size / 2;
                    boomEffs.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                    boomEffs.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }
                if (!player.updateHP(rocketHp)) {
                    player.setAlive(false);
                    sound.soundDestroy();
                    double x = player.getX() + Player.player_Size / 2;
                    double y = player.getY() + Player.player_Size / 2;
                    boomEffs.add(new Effect(x, y, 5, 5, 75, 0.05f, new Color(32, 178, 169)));
                    boomEffs.add(new Effect(x, y, 5, 5, 75, 0.1f, new Color(32, 178, 169)));
                    boomEffs.add(new Effect(x, y, 10, 10, 100, 0.3f, new Color(230, 207, 105)));
                    boomEffs.add(new Effect(x, y, 10, 5, 100, 0.5f, new Color(255, 70, 70)));
                    boomEffs.add(new Effect(x, y, 10, 5, 150, 0.2f, new Color(255, 255, 255)));
                }

            }
        }
    }

    private void drawBackground() {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        if (player.isAlive()) {
            player.draw(g2);
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
            }
        }
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                rocket.draw(g2);
            }
        }
        for (int i = 0; i < boomEffs.size(); i++) {
            Effect boomEffect = boomEffs.get(i);
            if (boomEffect != null) {
                boomEffect.draw(g2);
            }
        }
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 20f));
        g2.drawString("Score : " + score, 10, 20);
        if (!player.isAlive()) {
            String text = "GAME OVER";
            rockets.clear();
            String textKey = "Press key enter to Continue ...";
            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth) / 2;
            double y = (height - textHeight) / 2;
            g2.drawString(text, (int) x, (int) y + fm.getAscent());
            g2.setFont(getFont().deriveFont(Font.BOLD, 30f));
            fm = g2.getFontMetrics();
            r2 = fm.getStringBounds(textKey, g2);
            textWidth = r2.getWidth();
            textHeight = r2.getHeight();
            x = (width - textWidth) / 2;
            y = (height - textHeight) / 2;
            g2.drawString(textKey, (int) x, (int) y + fm.getAscent() + 50);
        } else if (score == 10) {
            String text = "YOU WIN!!!";
            rockets.clear();
            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth) / 2;
            double y = (height - textHeight) / 2;
            g2.drawString(text, (int) x, (int) y + fm.getAscent());
        }
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
