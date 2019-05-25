package info.hwebs.event;

public abstract class Visitor {

    public abstract void visit(TerrainVisitor visitor);

    public abstract void visit(EntityVisitor visitor);

    public final void visit(Visitor visitor) {
        visitor.visit(this);
    }
}
