package info.hwebs.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class Views {

    public static View tileViewOf(Image image) {
        assert(null != image);

        return new TileView(new ImageView(image));
    }
}


