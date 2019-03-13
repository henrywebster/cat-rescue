package code;




import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.image.Image;

public class Raft extends NonPlayer implements Observer{
	boolean occupied;
	
	Raft(int x, int y){
		this.x = x;
		this.y = y;
		state = "Swimming";
		image = new Image(getClass().getResourceAsStream(".." + File.separator + "images" + File.separator + "raft.png"),scale,scale,false,false);
	}
	
	public void setOccupied(){
		//tells if there is a player in the raft
		if(!occupied){
			occupied = true;
		}
		else{
			occupied = false;
		}
	}
	
	@Override
	public void setHit() {}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Player){
			if(occupied){
				x = ((Entity) o).getLocation().x;
				y = ((Entity) o).getLocation().y;
				moveSprite();
			}
			else
				game.movement(this,x,y);
				
		}
		
	}

}
