package info.hwebs.model;

import info.hwebs.ui.Window;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.sound.sampled.Clip;

public final class Models {

    // TODO change window thing
    public static Model tileModelOf(
            double x, double y, double size, Image image, Clip clip, Window window) {
        assert (size > 0.0);
        assert (null != image);
        assert (image.getHeight() > 0.0);
        assert (image.getHeight() == image.getWidth());

        final ImageView view = new ImageView(image);
        view.setFitHeight(size);
        view.setFitWidth(size);

        Model tile = new Tile(x, y, view, clip);
        tile.update();
        window.addToGameScene(view);
        return tile;
    }
}
