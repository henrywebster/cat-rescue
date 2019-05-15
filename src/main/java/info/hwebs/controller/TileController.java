package info.hwebs.controller;

import info.hwebs.view.View;
import info.hwebs.model.Model;

public final class TileController {

    private final View view;
    
    private final Model model;

    public TileController(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public void update() {
        this.view.update(model);
    }
}
