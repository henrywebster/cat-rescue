package info.hwebs.maps;

import info.hwebs.event.TerrainVisitor;
import info.hwebs.event.Visitor;
import info.hwebs.model.Model;

public final class MapTile {

    private final Model model;

    private final Terrain terrain;

    private final TerrainVisitor visitor;

    public MapTile(Model model, Terrain terrain) {
        this.model = model;
        this.terrain = terrain;
        this.visitor = new TerrainVisitor();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Visitor getVisitor() {
        return visitor;
    }
}
