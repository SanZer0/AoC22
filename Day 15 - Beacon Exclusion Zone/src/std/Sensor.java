/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Sensor {

	private int x;
	private int y;
	private int manhattan;
	Beacon closest;
	/**
	 * @param beacon 
	 * @param y2 
	 * @param x2 
	 * 
	 */
	public Sensor(int x2, int y2, Beacon beacon) {
		x = x2;
		y = y2;
		closest = beacon;
		manhattan = norm(x - beacon.x) + norm(y - beacon.y);
	}
	
	public int getManhattan() {
		return manhattan;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static int norm(int x) {
		if(x < 0) {
			return -x;
		}
		return x;
	}
	
	public String toString() {
		return x + " . " + y;
	}
}
