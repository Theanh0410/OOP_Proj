package Component;

public class Key {
    private boolean key_enter;
    private boolean key_accelerate;
    private boolean key_right;
    private boolean key_left;
    private boolean key_shoot;


    public boolean isKey_enter() {
        return key_enter;
    }

    public void setKey_enter(boolean key_enter) {
        this.key_enter = key_enter;
    }

    public boolean isKey_space() {
        return key_accelerate;
    }

    public void setKey_accelerate(boolean key_space) {
        this.key_accelerate = key_space;
    }

    public boolean isKey_right() {
        return key_right;
    }

    public void setKey_right(boolean key_right) {
        this.key_right = key_right;
    }

    public boolean isKey_left() {
        return key_left;
    }

    public void setKey_left(boolean key_left) {
        this.key_left = key_left;
    }

    public boolean isKey_shoot() {
        return key_shoot;
    }

    public void setKey_shoot(boolean key_shoot) {
        this.key_shoot = key_shoot;
    }
}
