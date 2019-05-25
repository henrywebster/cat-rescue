package info.hwebs.controller;

import info.hwebs.model.Direction;
import info.hwebs.model.Model;
import javafx.scene.shape.Rectangle;

final class EntityController extends AbstractController {

    private final Rectangle rec;

    EntityController(Model model, double boundX, double boundY) {
        super(model);
        this.rec = new Rectangle(0.0, 0.0, boundX, boundY);
    }

    @Override
    public void move() {
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
