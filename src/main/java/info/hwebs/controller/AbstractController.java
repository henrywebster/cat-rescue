package info.hwebs.controller;

import info.hwebs.model.Model;
import info.hwebs.model.Direction;

abstract class AbstractController implements Controller {

    final Model model;

    private AbstractController() {
        assert(false);
        this.model = null;
    }

    AbstractController(Model model) {
        this.model = model;
    } 

    @Override
    public void orient(Direction dir) {
        model.orient(dir);
    }
}
