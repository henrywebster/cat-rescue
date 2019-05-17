package info;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class Guard extends Enemy implements Observer {
    SearchStrategy strategy = new RandomStrategy();
    Point playerLocation;

    public Guard(int x, int y, String type) {
        super(x, y, type);
        state = "walking";
    }

    @Override
    public void move() {
        // movement method for the Guard class, randomly moves to a valid point on the map
        Point target = strategy.search(this.getLocation(), new Point(0, 0));

        int tries = 0;

        while (!state.equals("Inactive") && !game.movement(this, target.x, target.y)) {
            target = strategy.search(new Point(x, y), playerLocation);
            tries++;
            if (tries > 4)
                return; // the search strategy has 4 tries to pick a valid location to move to
        }

        x = target.x;
        y = target.y;

        moveSprite();
    }

    @Override
    public void update(Observable o, Object arg) {
        // if the player is in the water and within a certain radius, the shark will begin to chase
        // after the player
        if (((Player) o).getState().equals("walking")
                && (Math.abs(x - ((Player) o).getLocation().x)) < 5
                && Math.abs(y - ((Player) o).getLocation().y) < 5) {
            playerLocation = ((Player) o).getLocation();
            strategy = new PursuitStrategy();
        } else strategy = new RandomStrategy();
    }
}
