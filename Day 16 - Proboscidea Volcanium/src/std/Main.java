/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {

	public static int tick = 0;
	public static HashMap<State, Integer> cache;
	public static int valveAmount = 0;
	public static int valveAmountOverZero = 0;
	public static HashMap<Valve, HashMap<Valve, Integer>> distanceMap;
	public static List<Valve> valves;
	/**Use Dynamic Programming for this
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		valves = new ArrayList<Valve>();
		
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
		valveAmountOverZero = 0;
		for(Valve v : valves) {
			if(v.getName().equals("AA")) {
				startValve = v;			
			}
			if(v.getFlowRate() != 0) {
				valveAmountOverZero++;
			}
		}
		
		distanceMap = computeDistances(valves);
		//for(var a : distanceMap.entrySet()) {
		//	for(var b: a.getValue().entrySet()) {
		//		
		//		System.out.println("From "+ a.getKey().getName()+ " to " + b.getKey().getName()+ " in steps: " + b.getValue());
		//	}
		//}
		
		System.out.println("Max Flow is: " + calcFlow(30, startValve, new ArrayList<Valve>()));

	}
	
	private static HashMap<Valve, HashMap<Valve, Integer>> computeDistances(List<Valve> valves) {
		HashMap<Valve, HashMap<Valve, Integer>> distances = new HashMap<Valve, HashMap<Valve, Integer>>();
		for(Valve v: valves) {
			var thisMap = new HashMap<Valve, Integer>();
			distances.put(v, thisMap);
			int distance = 0;
			Queue<Tuple<Valve, Integer>> queue = new LinkedList<Tuple<Valve, Integer>>();
			List<Valve> done = new ArrayList<Valve>();
			queue.add(new Tuple(v, 0));
			while(!queue.isEmpty()) {
				var element = queue.remove();
				distances.get(v).put(element.x, element.y);
				done.add(element.x);
				for(Valve nb: element.x.getNeighbors()) {
					if(!done.contains(nb)) {
						queue.add(new Tuple(nb, element.y + 1));						
					}
				}
			}
		}
		return distances;
		
	}

	public static int calcFlow(int minutesLeft, Valve standpoint, List<Valve> openedValves) {
		if(openedValves.size() == valveAmountOverZero) {//eveything is opened, just calculate the rest
			return 0;
		}
		
		if(minutesLeft <= 0) {
			return 0;
		}
		State state = new State(minutesLeft, standpoint, openedValves);
		if(cache.get(state) != null) { //value already calculted
			return cache.get(state);
		}
		List<Integer> values = new ArrayList<Integer>();
		//go to a valve and open it
		for(Valve v: valves) {
			if(v.getFlowRate() == 0) {
				continue;
			}
			if(openedValves.contains(v)) {
				continue;
			}
			//open the next valve
			var minutesLeftAfterOpening = minutesLeft - distanceMap.get(standpoint).get(v) - 1;
			if(minutesLeftAfterOpening < 1) {
				continue;
			}
			var opened = new ArrayList<Valve>(openedValves);
			opened.add(v); //opened the valve
			int flow = calcFlow(minutesLeftAfterOpening, v, opened);
			int flowFromOpening = minutesLeftAfterOpening * v.getFlowRate();
			//System.out.println("Went from " + standpoint.getName() + " to " + v.getName());
			//System.out.println("Opened " + v.getName() + ". " + minutesLeftAfterOpening + " minutes left, adding " + flowFromOpening);
			//System.out.println("Found a max flow of " + flow);
			//for(Valve a : opened) {
			//	System.out.print(a.getName() + " " + v.getName());
			//}
			values.add(flow + flowFromOpening);
			//System.out.println();
		}
		
		
		int max = 0;
		//for(Integer a : values) {
		//	System.out.println(a);
		//}
		if(!values.isEmpty()) {
			max = Collections.max(values);
		}
		//System.out.println(max);
		//that's all
		cache.put(state, max);
		return max;
	}

}
