package code;




import java.awt.Point;
import java.util.Random;

public class RandomStrategy implements SearchStrategy{
	//Strategy that moves in a random direction
	@Override
	public Point search(Point loc, Point target) {
		Random ran = new Random();
		int delta = 1;
		int ranWay = ran.nextInt(2);
		int ranDir = ran.nextInt(2);
		
		if(ranWay == 0)
			delta*= -1;
		if(ranDir == 0)
			return new Point(loc.x+delta,loc.y);
		else
			return new Point(loc.x,loc.y+delta);
	}
	
	
}
