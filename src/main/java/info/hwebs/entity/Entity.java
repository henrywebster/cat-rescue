package info.hwebs.entity;

import java.awt.geom.Point2D;

import javafx.scene.image.Image;

//import javafx.collections.ObservableList;
//import javafx.scene.Node;


public abstract class Entity {
/*
	public void addToPane(ObservableList<Node> sceneGraph); //adds image representation to scene
	
	public void moveSprite(); //moves image representation
	
	public void hide(); //hides away from view as system gets rid of actual object
	
	public void setHit(); //when an entity hits another entity
	
	public Point removeFromBoard(); //removes itself from board grid when inactivated
	
	public String getState(); //returns the current state as a string of the object
	
	public Point getLocation(); //returns a point object of the x and y coordinates of the entity
*/
    private Point2D location; 
    
    private Image sprite;

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    Entity() {
        location = new Point2D.Float(0.0f, 0.0f);
    }

    Entity(float x, float y) {
        location = new Point2D.Float(x, y);
    }
}
