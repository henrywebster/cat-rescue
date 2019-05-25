package info.hwebs.controller;

import info.hwebs.model.Direction;
import info.hwebs.model.Model;
import java.util.logging.Logger;
import javafx.scene.shape.Rectangle;

final class TrackingController extends AbstractController {

    private static final Logger logger = Logger.getGlobal();

    private final Rectangle rec;

    private final double boundSize;

    private final double center;

    private final Model tracked;

    TrackingController(
            Model model, double boundX, double boundY, double boundSize, Model tracked) {
        super(model);
        this.boundSize = boundSize;
        this.rec = new Rectangle(0.0, 0.0, boundX, boundY);
        this.tracked = tracked;
        this.center = Math.floor(boundSize / 2.0);
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

        if (rec.contains(x, y)
                && rec.contains(x + boundSize - 1, y)
                && rec.contains(x + boundSize - 1, y + boundSize - 1)
                && rec.contains(x, y + boundSize - 1)) {
            if (x + center == tracked.getX()) {
                model.setX(x);
            }
            if (y + center == tracked.getY()) {
                model.setY(y);
            }
        }
    }
}
