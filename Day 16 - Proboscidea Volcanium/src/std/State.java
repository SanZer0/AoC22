/**
 * 
 */
package std;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jonas
 *
 */
public class State {

	
	int minutesLeft;
	Valve standpoint;
	List<Valve> openedValves;
	/**
	 * 
	 */
	public State(int minutesLeft, Valve standpoint, List<Valve> openedValves) {
		this.minutesLeft = minutesLeft;
		this.standpoint = standpoint;
		this.openedValves = new ArrayList<Valve>(openedValves);
	}

	
	public boolean equals(Object o) {
		if(!(o instanceof State)) {
			return false;
		}
		State comp = (State)o;
		if(minutesLeft == comp.minutesLeft && standpoint.equals(comp.standpoint) && openedValves.size() == comp.openedValves.size()) {
			for(Valve v: openedValves) {
				if(!comp.openedValves.contains(comp)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
