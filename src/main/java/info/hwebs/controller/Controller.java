package info.hwebs.controller;

import info.hwebs.model.Direction;

public interface Controller {

    public void move();

    public void orient(Direction dir);
}
