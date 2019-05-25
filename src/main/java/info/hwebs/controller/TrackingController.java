package info.hwebs.controller;

import info.hwebs.maps.GameMap;
import info.hwebs.model.Model;

final class TrackingController extends AbstractController {

    private final double boundSize;

    private final double center;

    private final Model tracked;

    private GameMap map;

    TrackingController(double boundSize, Model model, GameMap map, Model tracked) {
        super(model);
        this.boundSize = boundSize;
        this.map = map;
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

        if (map.contains(x, y)
                && map.contains(x + boundSize - 1, y)
                && map.contains(x + boundSize - 1, y + boundSize - 1)
                && map.contains(x, y + boundSize - 1)) {
            if (x + center == tracked.getX()) {
                model.setX(x);
            }
            if (y + center == tracked.getY()) {
                model.setY(y);
            }
        }
    }
}
