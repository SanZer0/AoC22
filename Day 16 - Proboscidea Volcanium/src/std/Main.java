/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {

	public static int tick = 0;
	public static HashMap<State, Integer> cache;
	public static int valveAmount = 0;
	/**Use Dynamic Programming for this
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input2.txt");
		Scanner sc = new Scanner(file);
		List<Valve> valves = new ArrayList<Valve>();
		
		while(sc.hasNextLine()) {
			String[] line = sc.nextLine().trim().split(" ");
			int fr = Integer.parseInt(line[4].replace(";", "").split("=")[1]);
			String name = line[1];
			Valve current = new Valve(name, fr);
			valves.add(current);
			for(int i = 9 ; i < line.length; i++) { //add the neighbors
				for(Valve v : valves) {
					if(v.getName().equals(line[i].replace(",", ""))) {
						//System.out.println("Adding " + v.getName() + " to " + current.getName());
						v.addNeighbor(current);
						current.addNeighbor(v);
					}
				}				
			}
		}
		sc.close();
		cache = new HashMap<State, Integer>();
		Valve startValve = null;
		valveAmount = valves.size();
		for(Valve v : valves) {
			if(v.getName().equals("AA")) {
				startValve = v;			
			}
		}
		
		System.out.println("Max Flow is: " + calcFlow(19, startValve, new ArrayList<Valve>(), 0));

	}
	
	public static int calcFlow(int minutesLeft, Valve standpoint, List<Valve> openedValves, int moved) {
		if(moved > valveAmount) {
			int flow = 0;
			for(Valve v: openedValves) {
				flow += v.getFlowRate();
			}
			return flow*minutesLeft;
		}
		if(minutesLeft == 1) {
			int flow = 0;
			for(Valve v: openedValves) {
				flow += v.getFlowRate();
			}
			return flow;
		}
		State state = new State(minutesLeft, standpoint, openedValves);
		if(cache.get(state) != null) { //value already calculted
			return cache.get(state);
		}
		//go to neighbor
		List<Integer> values = new ArrayList<Integer>();
		for(Valve v: standpoint.getNeighbors()) {
			values.add(calcFlow(minutesLeft - 1, v, new ArrayList<Valve>(openedValves), moved++));
		}
		//open valve
		if(standpoint.getFlowRate() != 0 && !openedValves.contains(standpoint)) {
			List<Valve> list = new ArrayList<Valve>(openedValves);
			list.add(standpoint);
			values.add(calcFlow(minutesLeft - 1, standpoint, list, 0));			
		}
		int flow = 0;
		for(Valve v: openedValves) {
			flow += v.getFlowRate();
		}
		int max = 0;
		if(!values.isEmpty()) {
			max = Collections.max(values);			
		}
		//System.out.println(flow + " + " + max);
		int result = flow + max;
		//that's all
		cache.put(state, result);
		return result;
	}

}
