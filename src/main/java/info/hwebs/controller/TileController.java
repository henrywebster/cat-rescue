package info.hwebs.controller;

import info.hwebs.model.Direction;
import info.hwebs.model.Model;
import javafx.scene.shape.Rectangle;

public final class TileController {

    private final Model model;

    // TODO: tmp
    private final Rectangle rec;

    public TileController(Model model) {
        this.model = model;
        this.rec = new Rectangle(0.0, 0.0, 13.0, 13.0);
    }

    public void orient(Direction dir) {
        model.orient(dir);
    }

    public void move() {
        // TODO stupid double check

        model.emitSound();

        final double xStart = model.getX();
        final double yStart = model.getY();
        double x = xStart;
        double y = yStart;
        switch (model.getOrientation()) {
            case LEFT:
                x -= 1.0;
                break;
            case RIGHT:
                x += 1.0;
                break;
            case UP:
                y -= 1.0;
                break;
            case DOWN:
                y += 1.0;
                break;
        }
        if (rec.contains(x, y)) {
            model.setX(x);
            model.setY(y);
        }
    }
}
