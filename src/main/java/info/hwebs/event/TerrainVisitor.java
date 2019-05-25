package info.hwebs.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class TerrainVisitor extends Visitor {

    // private static final Collection<Runnable> entityEventList = new ArrayList<>();

    private static final Map<Class<? extends Visitor>, Collection<Runnable>> eventMap =
            new HashMap<>();

    public static void addEvent(Class<? extends Visitor> clazz, Runnable event) {
        assert (null != event);
        eventMap.computeIfAbsent(clazz, (key) -> new ArrayList<Runnable>()).add(event);
    }

    @Override
    public void visit(EntityVisitor visitor) {
        System.out.println("Visited from entity");
        eventMap.computeIfAbsent(EntityVisitor.class, (key) -> new ArrayList<Runnable>())
                .forEach(Runnable::run);
    }

    @Override
    public void visit(TerrainVisitor visitor) {
        System.out.println("Visited from terrain");
        eventMap.computeIfAbsent(TerrainVisitor.class, (key) -> new ArrayList<Runnable>())
                .forEach(Runnable::run);
    }
}
