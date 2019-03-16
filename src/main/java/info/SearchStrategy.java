package info;




import java.awt.Point;

public interface SearchStrategy {
	//Strategy design pattern
	public Point search(Point loc, Point target);
}
