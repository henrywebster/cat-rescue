package info.hwebs.maps;

import info.hwebs.model.Models;
import info.hwebs.ui.Window;
import info.hwebs.ui.WindowContext;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public final class GameMap {

    // TODO: use buffer?
    public static GameMap load(
            double width,
            double height,
            Map<Terrain, Image> images,
            List<Integer> data,
            Window window) {
        assert (width > 0.0);
        assert (height > 0.0);
        assert (width * height <= data.size());
        assert (null != images);
        assert (null != data);

        final WindowContext context = window.getContext();

        final AtomicInteger xCounter = new AtomicInteger();
        final AtomicInteger yCounter = new AtomicInteger();
        return new GameMap(
                width,
                height,
                data.stream()
                        .map(
                                (number) -> {
                                    int x = xCounter.getAndIncrement();
                                    int y = yCounter.get();
                                    if (x > width) {
                                        xCounter.set(0);
                                        yCounter.getAndIncrement();
                                    }
                                    Terrain terrain;
                                    switch (number) {
                                        case 0:
                                            terrain = Terrain.GRASS;
                                            break;
                                        case 1:
                                            terrain = Terrain.SAND;
                                            break;
                                        default:
                                            terrain = Terrain.WATER;
                                    }
                                    assert (images.containsKey(Terrain.GRASS));
                                    return new MapTile(
                                            Models.spriteModelOf(
                                                    x,
                                                    y,
                                                    context.size,
                                                    images.get(terrain),
                                                    window),
                                            terrain);
                                })
                        .collect(Collectors.toList()));
    }

    private final List<MapTile> mapTiles;

    private final Rectangle boundry;

    private GameMap(double width, double height, List<MapTile> mapTiles) {

        this.boundry = new Rectangle(0.0, 0.0, width, height);
        this.mapTiles = mapTiles;
    }

    private GameMap() {
        assert (false);
        this.mapTiles = null;
        this.boundry = null;
    }

    public MapTile query(int x, int y) {
        final int index = x + y * (int) boundry.getWidth();
        assert (index < mapTiles.size());
        return mapTiles.get(index);
    }

    public boolean contains(double x, double y) {
        return x >= 0.0 && y >= 0.0 && boundry.contains(x, y);
    }
}
