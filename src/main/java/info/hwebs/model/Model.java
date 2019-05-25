package info.hwebs.model;

public interface Model {

    public double getX();

    public double getY();

    public void setX(double x);

    public void setY(double y);

    public void orient(Direction dir);

    public double getScale();

    public Direction getOrientation();

    //    public void emitSound();

    public void update();
}
