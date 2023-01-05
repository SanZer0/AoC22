/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Tile {

	int leftBlizzard = 0;
	int rightBlizzard = 0;
	int downBlizzard = 0;
	int upBlizzard = 0;
	int wall = 0;
	/**
	 * 
	 */
	public Tile(char initial) {
		if(initial == '<') {
			leftBlizzard = 1;
		}
		if(initial == '>') {
			rightBlizzard = 1;
		}
		if(initial == 'v') {
			downBlizzard = 1;
		}
		if(initial == '^') {
			upBlizzard = 1;
		}
		if(initial == '#') {
			wall = 1;
		}
	}
	
	public String toString() {
		int amount = leftBlizzard + rightBlizzard + downBlizzard + upBlizzard;
		if(amount == 0) {
			if(wall == 1) {
				return "#";
			}
			return ".";
		}
		if(amount == 1) {
			if(leftBlizzard == 1) {
				return "<";
			}
			if(rightBlizzard == 1) {
				return ">";
			}
			if(upBlizzard == 1) {
				return "^";
			}
			if(downBlizzard == 1) {
				return "v";
			}
		}
		if(amount > 1) {
			return "" + amount;
		}
		return "#";
	}
	
	public boolean hasBlizzard(int direction) {
		if(wall == 1) {return false;}
		switch(direction) {
			case 0: return rightBlizzard == 1;
			case 1: return downBlizzard == 1;
			case 2: return leftBlizzard == 1;
			case 3: return upBlizzard == 1;
			default: return false;
		}
	}

	public void setBlizzard(int direction, int toSet) {
		switch(direction) {
		case 0: rightBlizzard = toSet;
			break;
		case 1: downBlizzard = toSet;
			break;
		case 2: leftBlizzard = toSet;
			break;
		case 3: upBlizzard = toSet;
			break;
		}
	}
	
	public boolean isWall() {
		return wall == 1;
	}
	
	public void setWall(int toSet) {
		wall = toSet;
	}
	
	public boolean canBeMovedOn() {
		return rightBlizzard+leftBlizzard+downBlizzard+upBlizzard+wall == 0;
	}
}
