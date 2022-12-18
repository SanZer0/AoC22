/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Range {
	
	int startX;
	int endX;
	/**
	 * 
	 */
	public Range(int startX, int endX) {
		this.startX = startX;
		this.endX = endX;
	}
	
	public String toString() {
		return startX + " - " + endX;
	}
	
	public int getSize() {
		return Sensor.norm(endX - startX) + 1;
	}
	
	public boolean overlaps(Range r) {
		if(this.startX > r.startX && this.startX <= r.endX) {
			return true;
		} else if(this.startX <= r.startX && this.endX >= r.startX) {
			return true;
		}
		return false;
	}
	
	//combine two ranges
	public boolean combine(Range r) {
		if(this.overlaps(r)) {
			if(this.startX > r.startX) {
				this.startX = r.startX;
			}
			if(this.endX < r.endX) {
				this.endX = r.endX;
			}
			return true;
		} else {
			return false;
		}
	}
}
