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
        this.image = new ImageIcon(getClass().getResource("/src/Image/plane.png")).getImage();
        this.image_accelerate = new ImageIcon(getClass().getResource("/src/Image/plane_accelerate.png")).getImage();
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
}
