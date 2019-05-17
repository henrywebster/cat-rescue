package info;

import javafx.scene.image.Image;

public abstract class Enemy extends NonPlayer {
    int speed = 750;

    public Enemy(int x, int y, String type) {
        this.x = x;
        this.y = y;
        state = "walking";
        image =
                new Image(
                        getClass().getClassLoader().getResourceAsStream(type + ".png"),
                        scale,
                        scale,
                        false,
                        false);
    }

    public void setHit() {
        hide();
    }

    public abstract void move();
}
