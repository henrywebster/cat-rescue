package info.hwebs.event;

public final class EntityVisitor extends Visitor {

    // TODO make instance so different kinds can have different reactions
    //   private static Collection<Callable<Void>> terrainEventList = new ArrayList<>();

    //    public static addTerrainEvent(Callable<Void> terrainEvent) {
    //        assert(null != terrainEvent);
    //        terrainEventList.add(terrainEvent);
    //    }

    @Override
    public void visit(EntityVisitor visitor) {
        System.out.println("Visited by entity");
    }

    @Override
    public void visit(TerrainVisitor visitor) {
        System.out.println("Visited by terrain");
    }
}
