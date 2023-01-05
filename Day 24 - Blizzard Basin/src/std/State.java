/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class State {

	int x;
	int y;
	int mapNumber;
	int minutes;
	/**
	 * 
	 */
	public State(int x, int y, int mapNumber, int minutes) {
		this.x = x;
		this.y = y;
		this.mapNumber = mapNumber;
		this.minutes = minutes;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof State)) {
			return false;
		}
		State comp = (State) o;
		if(this.x == comp.x && this.y == comp.y && this.mapNumber == comp.mapNumber) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "" + x + " " + y + " " + mapNumber;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + mapNumber;
        return result;
    }

}
