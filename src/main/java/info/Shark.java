package info;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class Shark extends Enemy implements Observer {

    SearchStrategy strategy = new PursuitStrategy();
    Point playerLocation;

    public Shark(int x, int y, String type) {
        // Shark constructor, set shark as swimming
        super(x, y, type);
        state = "Swimming";
    }

    @Override
    public void move() {
        if (playerLocation != null) {
            Point target = strategy.search(new Point(x, y), playerLocation);

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
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player)
            // if the player is in the water and within a certain radius, the shark will begin to
            // chase after the player
            if (((Player) o).getState().equals("Swimming")
                    && (Math.abs(x - ((Player) o).getLocation().x)) < 10
                    && Math.abs(y - ((Player) o).getLocation().y) < 10)
                playerLocation = ((Player) o).getLocation();
    }
}
