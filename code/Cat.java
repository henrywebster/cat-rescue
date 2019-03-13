package code;



import java.awt.Point;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.Image;

public class Cat extends NonPlayer implements Observer{
	public Cat follower;
	Entity leader;
	Point prevLocation;
	Point playerLocation;
	boolean isFollowing;


	
	public Cat(int x, int y,String type){
		this.x = x;
		this.y = y;
		state = "walking";
		image = new Image(getClass().getResourceAsStream(".." + File.separator + "images" + File.separator + "cat_" + type + ".png"),scale,scale,false,false);
	
	}
	
	public void setFollower(Cat f){
		if(this==f) return;
		if(this.follower == null){
			follower = f;
			follower.leader = this;
		}
		
		this.follower.setFollower(f);
	}
	
	public void removeFollower(){
		if(this.follower != null && this.follower.follower == null){
			this.follower.hide();
			this.follower = null;
		}
		else
			follower.removeFollower();
	}
	
	public Cat getFollower(){
		return follower;
	}
	
	public void embark(){
		state = "Swimming";
		game.movement(this, playerLocation.x, playerLocation.y);
		x=0;
		y=0;
		sprite.toBack();
		if(this.follower == null)
			return;
		
		this.follower.embark();
	}
	
	public void disembark(){
		state = "walking";
		
		if(this.follower == null)
			return;
		
		this.follower.disembark();
	}
	
	public void setHit(){
		leader.setHit();
	}
		/*
		if(follower == null){
			hide();
		}
		if(follower != null && follower.getFollower() == null){

			follower.hide();
			follower = null;
			return;
		}
		else if(follower != null){
			follower.setHit();
		}
	}
	*/
	
	public boolean getFollowing(){
		return isFollowing;
	}
	
	public int catCount(){
		if(follower == null)
			return 1;
		return 1 + follower.catCount();
	}
	
	
	public boolean checkFound(Point p){
		if(p.getX() -1 == this.x &&
				p.getY() == this.y ||
				p.getX() + 1 == this.x &&
				p.getY() == this.y ||
				p.getX() == this.x &&
				p.getY() - 1 == this.y ||
				p.getX() == this.x &&
				p.getY() + 1== this.y)
			return true;
		return false;
	}
	
	public void move(Point p){
		if(!state.equals("Inactive")){
			if(state.equals("walking")){
				game.movement(this, p.x, p.y);
				prevLocation = new Point(x,y);
				sprite.toFront();
				if(new Point(x,y).equals(p))
					return;
			
				x = p.x;
				y = p.y;

			}
			else{

			}
			moveSprite();
		
			if(follower != null){
				follower.move(this.prevLocation);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Player && !isFollowing && checkFound(((Player) o).getLocation())){
			isFollowing = true;
			((Player)o).setFollower(this);
			playerLocation = ((Player)o).getLocation();
		}
		if(follower != null && follower.getState() == "Inactive")
			follower=null;
		
	}


}
