package info.hwebs.controller;

import info.hwebs.event.EntityVisitor;
import info.hwebs.maps.GameMap;
import info.hwebs.maps.MapTile;
import info.hwebs.maps.Terrain;
import info.hwebs.model.Model;
import java.util.Set;

final class EntityController extends AbstractController {

    private GameMap map;

    private final Set<Terrain> allowed;

    private final EntityVisitor visitor;

    EntityController(Model model, GameMap map, Set<Terrain> allowed) {
        super(model);
        this.map = map;
        this.allowed = allowed;
        this.visitor = new EntityVisitor();
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

        if (map.contains(x, y)) {
            final MapTile tile = map.query((int) x, (int) y);
            if (allowed.contains(tile.getTerrain())) {
                model.setX(x);
                model.setY(y);
                tile.getVisitor().visit(visitor);
            }
        }
    }
}
