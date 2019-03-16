package info;




import java.awt.Point;
import java.util.Random;

public class PursuitStrategy implements SearchStrategy{

	/*
	 * (non-Javadoc)
	 * @see main.java.SearchStrategy#search(java.awt.Point, java.awt.Point)
	 * Pursuit Strategy returns a point nearer to the target point so the object using it can "chase" after the player
	 */
	@Override
	public Point search(Point loc,Point target) {
		Random ran = new Random();
		int ranDir = ran.nextInt(2);
		
		
		boolean furtherX = (target.x - loc.x) > 0;
		boolean furtherY = (target.y - loc.y) > 0;

		
		if(ranDir == 0)
			return (furtherX) ? new Point(loc.x+1,loc.y): new Point(loc.x-1,loc.y);
		else
			return (furtherY) ? new Point(loc.x,loc.y+1): new Point(loc.x,loc.y-1);
	}

}
