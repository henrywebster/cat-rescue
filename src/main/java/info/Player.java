package info;




import java.awt.Point;
import java.io.File;
import java.util.Observable;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class Player extends Observable implements Entity{
	private int x;private int y;
	String state;
	private int scale;
	private Point prevLocation = new Point(0,0);
	int catsGained = 0;
	public Cat follower;
	Raft raft;
	
	Game game = Game.getInstance();
	
	ImageView sprite;
	Image image;
	
	public Player(int x, int y){
		//constructor for player object, the coordinate is defined in the map image
		this.x = x;
		this.y = y;
		this.scale = game.getScale();
		this.state = "walking";
		this.image = new Image(getClass().getClassLoader().getResourceAsStream("player.png"),scale,scale,false,false);
		setChanged();
	}
	
	public Point removeFromBoard(){
		return new Point(x,y);
	}
	
	public void addToPane(ObservableList<Node> sceneGraph){
		//adds the sprite to the scene at the objects location
		sprite = new ImageView(image);
		sprite.setX(x*scale);
		sprite.setY(y*scale);
		sceneGraph.add(sprite);
	}
	
	public String getState(){
		//returns state of the player
		return state;
	}
	
	public void embark(Raft r){
		//the player is now on the raft and the followers all compress into the raft
		state = "Swimming";
		if(raft == null){
			raft = r;
			raft.setOccupied();
		}
		if(follower != null)
			follower.embark();
	}
	
	public void disembark(){
		//player leaves the raft and the followers trail behind
		state = "walking";
		raft.setOccupied();
		raft = null;
		if(follower != null)
			follower.disembark();
	}
	
	public void setHit(){
		//the player has been contacted
		if(this.follower == null){
			hide();
			game.gameOver();
			return;
		}
		if(follower.follower == null){
			follower.hide();
			follower = null;
			return;
		}
		follower.removeFollower();
	}
	
	public int getCatsGained() {
		return catsGained;
	}
	
	public Point getLocation(){
		return new Point(x,y);
	}
	
	public void setFollower(Cat f){
		//cat found, add to list of cats
		catsGained++;
		if(follower == null){
			follower = f;
			follower.leader = this;
			return;
		}
		follower.setFollower(f);
	}
	
	public void hide(){
		//dead
		state = "Inactive";
		x = 0;
		y = 0;
		moveSprite();
	}
	
	public void moveSprite(){
		//moves the image, works multi-threaded in case I want to do that
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				sprite.setX(x*scale);
				sprite.setY(y*scale);	
			}
		}
				);
	}
	
	public void move(KeyEvent ke){
		//depending on what key is pressed, the player moves differently
			switch(ke.getCode()){
				case W:
					if(game.movement(this, x, y-1)){
						this.prevLocation = new Point(x,y);
						y--;
					}
					break;
				case S:
					if(game.movement(this, x, y+1)){
						this.prevLocation = new Point(x,y);
						y++;
					}
					break;
				case A:
					if(game.movement(this, x-1, y)){
						this.prevLocation = new Point(x,y);
						x--;
					}
					break;
				case D:
					if(game.movement(this, x+1, y)){
						this.prevLocation = new Point(x,y);
						x++;
					}
					break;
				default:
					break;
			}
				
			
			if(follower != null){
				follower.move(this.prevLocation);
			}
			
			setChanged();
			notifyObservers();		
			moveSprite();
			
			
	}
}
