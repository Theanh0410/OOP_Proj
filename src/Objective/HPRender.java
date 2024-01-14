package Objective;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HPRender {
    private final HP hp;

    public HPRender(HP hp) {
        this.hp = hp;
    }

    protected void HPRender(Graphics2D g2, Shape shape, double y) {
        if (hp.getCurrentHP() != hp.getMAX_HP()) {
            double hpY = shape.getBounds().getY() - y - 10;
            g2.setColor(new Color(70, 70, 70));
            g2.fill(new Rectangle2D.Double(0, hpY, Player.));
        }
    }
}
