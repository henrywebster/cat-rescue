package info.hwebs.controller;

import info.hwebs.maps.GameMap;
import info.hwebs.maps.Terrain;
import info.hwebs.model.Model;
import java.util.Set;

public final class Controllers {

    public static TrackingController trackingControllerOf(
            double boundSize, Model model, Model tracked, GameMap map) {
        assert (boundSize > 0.0);
        assert (null != model);
        assert (null != tracked);
        assert (null != map);

        return new TrackingController(boundSize, model, map, tracked);
    }

    public static EntityController entityControllerOf(
            Model model, GameMap map, Set<Terrain> allowed) {
        assert (null != model);
        assert (null != map);
        assert (null != allowed);

        return new EntityController(model, map, allowed);
    }
}
