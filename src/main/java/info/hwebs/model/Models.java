package info.hwebs.model;

import info.hwebs.ui.Window;
import javafx.scene.Camera;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class Models {

    // TODO change window thing
    // TODO standardize param order
    public static SpriteModel spriteModelOf(
            double x, double y, double size, Image image, Window window) {
        assert (size > 0.0);
        assert (null != image);
        assert (image.getHeight() > 0.0);
        assert (image.getHeight() == image.getWidth());

        final ImageView view = new ImageView(image);
        view.setFitHeight(size);
        view.setFitWidth(size);

        final SpriteModel sprite = new SpriteModel(x, y, view);
        sprite.update();
        window.addToGameScene(view);
        return sprite;
    }

    public static CameraModel cameraModelOf(double x, double y, double tileSize, Camera camera) {
        // TODO asserts

        CameraModel cameraModel = new CameraModel(x, y, camera, tileSize);
        cameraModel.update();
        return cameraModel;
    }
}
