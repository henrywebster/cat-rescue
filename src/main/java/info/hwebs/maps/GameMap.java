package info.hwebs.maps;

import java.util.List;

import info.hwebs.model.Model;

import javafx.scene.shape.Rectangle;

public final class GameMap {

    private final List<Model> terrain;

    private final Rectangle boundry;

    public GameMap(double width, double height, List<Model> terrain) {
    
        this.boundry = new Rectangle(0.0, 0.0, width, height);
        this.terrain = terrain;
    }

    public Model query(int x, int y) {
        final int index = x * (int)boundry.getHeight() + y;
        assert(index < terrain.size());
        return terrain.get(x);
    }
}
