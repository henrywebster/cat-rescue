package info.hwebs.controller;

import info.hwebs.model.Model;

public final class Controllers {

    public static TrackingController trackingControllerOf(Model model, Model tracked, double boundX, double boundY, double boundSize) {
        assert(null != model);
        assert(null != tracked);
        assert(boundX > 0.0);
        assert(boundY > 0.0);
        assert(boundSize > 0.0);

        return new TrackingController(model, boundX, boundY, boundSize, tracked);
    }

    public static EntityController entityControllerOf(Model model, double boundX, double boundY) {
        assert(null != model);
        assert(boundX > 0.0);
        assert(boundY > 0.0);

        return new EntityController(model, boundX, boundY);
    }
}
