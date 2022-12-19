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
		System.out.println(calcFlow2(26, 26, startValve, startValve, new ArrayList<Valve>()));

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
			values.add(flow + flowFromOpening);
		}
		
		
		int max = 0;
		if(!values.isEmpty()) {
			max = Collections.max(values);
		}
		return max;
	}
	
	public static int calcFlow2(int minutesHuman, int minutesElephant, Valve standpointH, Valve standpointE, List<Valve> openedValves) {
		//System.out.println(minutesHuman + " " + minutesElephant);
		if(openedValves.size() == valveAmountOverZero) {//eveything is opened, just calculate the rest
			return 0;
		}
		
		if(minutesHuman <= 0 && minutesElephant <= 0) {
			return 0;
		}
		List<Integer> values = new ArrayList<Integer>();
		//choose a valve for the human and open it
		//go one by one, start with the one that has more minutes
		if(minutesHuman >= minutesElephant) {
			for(Valve v: valves) { //choose a valve for the human
				var opened = new ArrayList<Valve>(openedValves);
				if(v.getFlowRate() == 0) {
					continue;
				}
				if(openedValves.contains(v)) {
					continue;
				}
				//open it as a human
				var minutesLeftAfterOpening = minutesHuman - distanceMap.get(standpointH).get(v) - 1;
				if(minutesLeftAfterOpening < 1) {
					continue;
				}
				int flowFromOpening = minutesLeftAfterOpening * v.getFlowRate(); //calculate the added flow
				opened.add(v); //opened the valve
				//choose the next valve
				//System.out.println("Human opened " + v.getName());
				int flow = calcFlow2(minutesLeftAfterOpening, minutesElephant, v, standpointE, opened);
				values.add(flow + flowFromOpening);
				//int flow = calcFlow(minutesLeftAfterOpening, v, opened);
			}
		} else { //copy paste for elephant
			for(Valve v: valves) { //choose a valve for the human
				var opened = new ArrayList<Valve>(openedValves);
				if(v.getFlowRate() == 0) {
					continue;
				}
				if(openedValves.contains(v)) {
					continue;
				}
				//open it as a human
				var minutesLeftAfterOpening = minutesElephant - distanceMap.get(standpointE).get(v) - 1;
				if(minutesLeftAfterOpening < 1) {
					continue;
				}
				int flowFromOpening = minutesLeftAfterOpening * v.getFlowRate(); //calculate the added flow
				opened.add(v); //opened the valve
				//choose the next valve
				//System.out.println("Elephant opened " + v.getName());
				int flow = calcFlow2(minutesHuman, minutesLeftAfterOpening, standpointH, v, opened);
				values.add(flow + flowFromOpening);
				//int flow = calcFlow(minutesLeftAfterOpening, v, opened);
			}
		}
		
		
		int max = 0;
		if(!values.isEmpty()) {
			max = Collections.max(values);
		}
		return max;
	}

}
