/**
 * 
 */
package std;

/**
 * @author jonas
 *
 */
public class Coordinate {

	
	int x;
	int y;
	/**
	 * 
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "x: " + x + " y: " + y; 
	}
	
	public boolean equals(Object o) {
		if(o instanceof Coordinate) {
			Coordinate comp = (Coordinate) o;
			if(this.x == comp.x && this.y == comp.y) {
				return true;
			}
		}
		return false;
	}
}
