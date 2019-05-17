package info.hwebs.entity;

import java.util.List;
import java.util.Vector;

public class EntityManager {

    private static final List<Entity> active;

    private static final List<Entity> reserve;

    private static final int INIT_ACTV_CAPACITY = 20;
    private static final int INIT_RESV_CAPACITY = 20;

    static {
        active = new Vector<>(INIT_ACTV_CAPACITY);
        reserve = new Vector<>(INIT_RESV_CAPACITY);
    }

    private EntityManager() {}

    public Entity createGuard() {
        return null;
    }
}
