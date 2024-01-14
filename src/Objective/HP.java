package Objective;

public class HP {
    private double max_HP;
    private double current_HP;

    public HP(double max_HP, double currentHP) {
        this.max_HP = max_HP;
        this.current_HP = currentHP;
    }

    public HP() {
    }

    public double getMax_HP() {
        return max_HP;
    }

    public double getCurrentHP() {
        return current_HP;
    }

    public void setCurrentHP(double currentHP) {
        this.current_HP = currentHP;
    }
}
