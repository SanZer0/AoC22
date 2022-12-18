/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Beacon {
	
	public int x;
	public int y;
	/**
	 * @param beacy 
	 * @param beacx 
	 * 
	 */
	public Beacon(int beacx, int beacy) {
		x = beacx;
		y = beacy;
	}
	
	public boolean equals(Object b) {
		if(!(b instanceof Beacon)) {
			return false;
		}
		if(this.x == ((Beacon)b).x && this.y == ((Beacon)b).y) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return x  + " " + y;
	}
}
