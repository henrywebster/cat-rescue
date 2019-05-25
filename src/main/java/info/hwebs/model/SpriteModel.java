package info.hwebs.model;

import javafx.scene.image.ImageView;

public final class SpriteModel extends AbstractModel {

    SpriteModel(double x, double y, ImageView view) {
        super(x, y, view);
    }

    @Override
    public void update() {
        assert (this.node instanceof ImageView);
        final ImageView view = (ImageView) node;
        node.relocate(x * view.getFitWidth(), y * view.getFitHeight());
    }
}
