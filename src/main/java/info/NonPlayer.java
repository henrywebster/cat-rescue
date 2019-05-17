package info;

import java.awt.Point;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class NonPlayer implements Entity {
    int x;
    int y;

    String state;
    Game game = Game.getInstance();
    int scale = game.getScale();
    ImageView sprite;
    Image image;

    public void addToPane(ObservableList<Node> sceneGraph) {

        this.sprite = new ImageView(image);
        scale = game.getScale();

        moveSprite();
        sceneGraph.add(sprite);
    }

    public Point removeFromBoard() {
        return new Point(x, y);
    }

    public String getState() {
        return state;
    }

    public void moveSprite() {
        Platform.runLater(
                new Runnable() {

                    @Override
                    public void run() {
                        sprite.setX(x * scale);
                        sprite.setY(y * scale);
                    }
                });
    }

    public synchronized void hide() {
        // map.setOccupy(x,y,null);
        // map.remove(this);
        game.removeFromBoard(new Point(x, y));
        state = "Inactive";
        x = 1;
        y = 1;
        sprite.toBack();
        // moveSprite();
    }

    public Point getLocation() {
        return new Point(x, y);
    }
}
