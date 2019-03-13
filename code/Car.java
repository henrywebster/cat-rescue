package code;



public class Car extends Enemy{
	int direction = 1;
	boolean goingY = true;
	int rotation;
	int speed = 100;
	
	public Car(int x, int y, String type) {
		super(x, y, type);
		state = "Driving";
	}

	@Override
	public void move() {
		
		
		//rotates movement
		
		switch(direction){
		case 0:
			//left
			if(game.movement(this,x-1,y))
				x--;
			else
				direction = 3;
			break;
		case 1:
			//right
			if(game.movement(this,x+1,y))
				x++;
			else
				direction = 2;
			break;
		case 2:
			//up
			if(game.movement(this,x,y-1))
				y--;
			else
				direction = 0;
			break;
		case 3:
			//down
			if(game.movement(this,x,y+1))
				y++;
			else
				direction = 1;
			break;
		}
		moveSprite();	
		
	}

}
