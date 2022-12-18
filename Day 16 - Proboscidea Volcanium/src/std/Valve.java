/**
 * 
 */
package std;

import java.util.LinkedList;
import java.util.List;

/**
 * @author SanZer0
 *
 */
public class Valve {

	private List<Valve> neighbors;
	private String name;
	private int flowRate;
	/**
	 * 
	 */
	public Valve(String name, int fr) {
		neighbors = new LinkedList<>();
		this.name = name;
		this.flowRate= fr;
	}
	
	public int getFlowRate() {
		return flowRate;
	}
	public void addNeighbor(Valve nb) {
		neighbors.add(nb);
	}
	
	public List<Valve> getNeighbors() {
		return neighbors;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Object o) {
		//System.out.println(this.name + " " + ((Valve)o).getName());
		if(!(o instanceof Valve)) {
			return false;
		}
		if(this.name.equals(((Valve)o).getName())) {
			return true;
		}
		return false;
	}

}
