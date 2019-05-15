package info.hwebs.model;

public abstract class AbstractModel implements Model {

    private double x;

    private double y;

    AbstractModel(double x, double y) {
        this.x = x;
        this.y = y;
    }

    AbstractModel() {
        this.x = 0.0;
        this.y = 0.0;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
} 
