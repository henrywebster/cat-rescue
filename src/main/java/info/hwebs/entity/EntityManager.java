package info.hwebs.entity;

import java.util.List;
import java.util.Vector;

public class EntityManager {


    private final static List<Entity> active;

    private final static List<Entity> reserve;

    private final static int INIT_ACTV_CAPACITY = 20;
    private final static int INIT_RESV_CAPACITY = 20;

    static {
        active = new Vector<>(INIT_ACTV_CAPACITY);
        reserve = new Vector<>(INIT_RESV_CAPACITY);
    }

    private EntityManager() {}

    public Entity createGuard() {
        return null;
    }

}
