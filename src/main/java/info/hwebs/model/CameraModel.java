package info.hwebs.model;

import javafx.scene.Camera;

public final class CameraModel extends AbstractModel {

    private double tileSize;

    CameraModel(double x, double y, Camera camera, double tileSize) {
        super(x, y, camera);
        this.tileSize = tileSize;
    }

    private CameraModel() {
        super(0, 0, null);
    }

    // TODO will have to calculate scale if need be
    @Override
    public void update() {
        node.relocate(x * tileSize, y * tileSize);
    }
}
