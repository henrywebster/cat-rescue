package info.hwebs.model;

import javafx.scene.Node;

public abstract class AbstractModel implements Model {

    protected double x;

    protected double y;

    private double scale;

    protected final Node node;

    //    private final Clip clip;

    private Direction orientation;

    AbstractModel(double x, double y, Node node) {
        this.x = x;
        this.y = y;
        this.scale = 1.0;
        this.node = node;
        //        this.clip = clip;
        this.orientation = Direction.UP;
    }

    AbstractModel(Node node) {
        this.x = 0.0;
        this.y = 0.0;
        this.scale = 1.0;
        this.node = node;
        //      this.clip = null;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void update() {
        this.node.relocate(x, y);
    }

    @Override
    public void orient(Direction orientation) {
        this.orientation = orientation;
    }

    @Override
    public Direction getOrientation() {
        return orientation;
    }

    //    @Override
    //    public void emitSound() {
    //        Audio.submit(clip);
    //    }
}
