/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Cube {
	
	
	public int x;
	public int y;
	public int z;
	public boolean isLava = false;
	public boolean touchesAir = false;
	/**
	 * 
	 */
	public Cube(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public boolean equals(Object a) {
		if(!(a instanceof Cube)) {
			return false;
		}
		Cube o = (Cube)a;
		if(this.x == o.x && this.y == o.y && this.z == o.z) {
			return true;
		}
		return false;
	}
}
