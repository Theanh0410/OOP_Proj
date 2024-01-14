package Objective;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.ImageIcon;

public class Player extends HPRender{
    public static final double player_Size = 64;
    private double x;
    private double y;
    private final float MAX_SPEED = 1f;
    private float speed = 0f;
    private float angle = 0f;
    private final Area playerShape;
    private final Image image;
    private final Image image_accelerate;
    private boolean speedUp;
    private boolean alive = true;

    public Player() {
        super(new HP(50, 50));
        this.image = new ImageIcon(getClass().getResource("/Image/plane.png")).getImage();
        this.image_accelerate = new ImageIcon(getClass().getResource("/Image/plane_accelerate.png")).getImage();
        Path2D p = new Path2D.Double();
        p.moveTo(0, 15);
        p.lineTo(20, 5);
        p.lineTo(player_Size + 15, player_Size / 2);
        p.lineTo(20, player_Size - 5);
        p.lineTo(0, player_Size - 15);
        playerShape = new Area(p);
    }

    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }
    public void update() {
        x += Math.cos(Math.toRadians(angle))*speed;
        y += Math.sin(Math.toRadians(angle))*speed;
    }

    public void changeAngle(float angle) {
        if (angle < 0) {
            angle = 359;
        } else if (angle < 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), player_Size / 2, player_Size / 2);
        g2.drawImage(speedUp ? image_accelerate : image, tran, null);
        hpRender(g2, getShape(), y);
        g2.setTransform(oldTransform);
    }

    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), player_Size / 2, player_Size / 2);
        return new Area(afx.createTransformedShape(playerShape));
    }

    public void speedUp() {
        speedUp = true;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        } else {
            speed += 0.01f;
        }
    }

    public void speedDown() {
        speedUp = false;
        if (speed <= 0) {
            speed = 0;
        } else {
            speed -= 0.003f;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void reset() {
        alive = true;
        resetHP();
        angle = 0;
        speed = 0;
    }
}
