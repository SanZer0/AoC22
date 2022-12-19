package std;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Rock {
	
	Point[] shape;
	final int shapeNumber;
	List<Tuple<Integer, Integer>> coords;
	//these will always default to the bottom-left corner by construction
	int x;//x position from the leftmost #
	int y;//y position from the downmost #
	
	//constructor
	public Rock(int shape, int x, int y) {
		this.x = x;
		this.y = y;
		shapeNumber = shape;
		updateCoords();
	}	
	
	public Point[] getCoords() {
		return shape;
	}
	
	public void setCoords(Point[] shape) {
		this.shape = shape;
	}
	
	public int[] xRange() {
		int maxX = 0;
		for(Point p: shape) {
			maxX = Math.max(p.x, maxX);
		}
		int[] result = {this.x, maxX};
		return result;
	}
	
	public int[] yRange() {
		int maxY = 0;
		for(Point p: shape) {
			maxY = Math.max(p.y, maxY);
		}
		int[] result = {this.y, maxY};
		return result;
	}
	
	public void updateCoords() {
		this.shape = getShape(shapeNumber);
		//update positions based on x and y
		for(Point p: this.shape) {
			p.x = p.x + this.x;
			p.y = p.y + this.y;
		}
	}
	
	public Point[] getShape(int number) {
		Point[][] sprites = 
			{{new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0)},
			{new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
			{new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(2, 1), new Point(2, 2)},
			{new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)},
			{new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1)}};
		return sprites[number];
	}
	
	
}
