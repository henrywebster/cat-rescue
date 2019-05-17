package info;

import java.awt.Point;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public interface Entity {

    public void addToPane(ObservableList<Node> sceneGraph); // adds image representation to scene

    public void moveSprite(); // moves image representation

    public void hide(); // hides away from view as system gets rid of actual object

    public void setHit(); // when an entity hits another entity

    public Point removeFromBoard(); // removes itself from board grid when inactivated

    public String getState(); // returns the current state as a string of the object

    public Point getLocation(); // returns a point object of the x and y coordinates of the entity
}
