package info;





import java.io.File;

import javafx.scene.image.Image;


public abstract class Enemy extends NonPlayer{
	int speed = 750;

	public Enemy(int x, int y, String type){
		this.x = x;
		this.y = y;
		state = "walking";
		image = new Image(getClass().getResourceAsStream(".." + File.separator + "images" + File.separator + type + ".png"),scale,scale,false,false);
	}
	
	public void setHit(){
		hide();
	}
	
	public abstract void move();
	
}


