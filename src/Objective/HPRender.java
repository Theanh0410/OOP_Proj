package Objective;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HPRender {
    private final HP hp;

    public HPRender(HP hp) {
        this.hp = hp;
    }

    protected void hpRender(Graphics2D g2, Shape shape, double y) {
        if (hp.getCurrentHP() != hp.getMax_HP()) {
            double hpY = shape.getBounds().getY() - y - 10;
            g2.setColor(new Color(70, 70, 70));
            g2.fill(new Rectangle2D.Double(0, hpY, Player.player_Size, 2));
            g2.setColor(new Color(253, 91, 91));
            double hpSize = hp.getCurrentHP() / hp.getMax_HP() * Player.player_Size;
            g2.fill(new Rectangle2D.Double(0, hpY, hpSize, 2));
        }
    }

    public boolean updateHP(double cutHP) {
        hp.setCurrentHP(hp.getCurrentHP() - cutHP);
        return hp.getCurrentHP() > 0;
    }

    public double getHP() {
        return hp.getCurrentHP();
    }

    public void resetHP() {
        hp.setCurrentHP(hp.getMax_HP());
    }
}
