package info.hwebs.map;

import javafx.geometry.BoundingBox;

public class Map {

    private final BoundingBox bounds;

    public Map(int width, int height, double tileSize) {
        this.bounds = new BoundingBox(0.0, 0.0, width * tileSize, height * tileSize);
    }

    public BoundingBox getBounds() {
        return this.bounds;
    }
}
