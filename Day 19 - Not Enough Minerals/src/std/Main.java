/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {
	
	static List<Blueprint> blueprintList; 
	static String inputFileString;
	static int[] maximumRobotsNeeded;
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		inputFileString = "input.txt";
		setupBlueprints();
		int[] robots = {1, 0, 0, 0};
		int[] minerals = {0, 0, 0, 0};
		int quality = 0;
		int partTwo = 3;
		int quality2 = 1;
		int result = 0;
		for(Blueprint bp : blueprintList) {
			result = calculateQuality(bp, 32);
			quality += result * bp.blueprintNumber;
			quality2 *= result;
			partTwo--;
			System.out.println(result);
			if(partTwo == 0) {
				break;
			}
		}
		//System.out.println(quality);
		System.out.println(quality2);
	}
	
	public static void setInputFile(String inputFile) {
		inputFileString = inputFile;
	}
	
	//get the blueprints from the input file
	public static void setupBlueprints() throws FileNotFoundException {
		File file = new File(inputFileString);
		Scanner sc = new Scanner(file);
		blueprintList = new ArrayList<>();
		//one blueprint per line
		while(sc.hasNextLine()) {
			String blueprintText = sc.nextLine();
			String[] splitText = blueprintText.trim().split(" ");
			int blueprintNumber = Integer.parseInt(splitText[1].replace(":", ""));
			int oreCostOre = Integer.parseInt(splitText[6]);
			int clayCostOre = Integer.parseInt(splitText[12]);
			int obsidianCostOre = Integer.parseInt(splitText[18]);
			int obsidianCostClay = Integer.parseInt(splitText[21]);
			int geodeCostOre = Integer.parseInt(splitText[27]);
			int geodeCostObsidian = Integer.parseInt(splitText[30]);
			blueprintList.add(new Blueprint(blueprintNumber, oreCostOre, clayCostOre, obsidianCostOre, obsidianCostClay, geodeCostOre, geodeCostObsidian));
		}
	}
	
	public static int calculateQuality(Blueprint bp, int minutes) {
		Queue<State> queue = new LinkedList<State>();
		int maxGeodes = Integer.MIN_VALUE;
		queue.add(new State(bp, minutes));
		while(!queue.isEmpty()) {
			State currentState = queue.remove();
			//this small optimization does god's work, just cut off the branches that can't be good anyway
			int globalMax = currentState.minerals[3];
			for(int i = currentState.minutes; i > 0; i--) {
				globalMax += currentState.robots[3] + (currentState.minutes-i);
			}
			if(globalMax <= maxGeodes) {
				continue;
			}
			
			if(currentState.minutes <= 0) {
				maxGeodes = Math.max(maxGeodes, currentState.getGeodes());
				continue;
			}	
			
			//buy geode robot
			State stateNew = currentState.copy();
			while(!stateNew.canConstruct(3) && stateNew.robots[0] > 0 && stateNew.robots[2] > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0];
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
			}
			if(stateNew.canConstruct(3) && stateNew.minutes > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0] - stateNew.blueprint.geodeCostOre;
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2] - stateNew.blueprint.geodeCostObsidian;
				stateNew.minerals[3] += stateNew.robots[3];
				stateNew.robots[3]++;
				queue.add(stateNew);
				//System.out.println("Bought geode with " + stateNew.minutes);
			}		
			//buy obsidian robot
			stateNew = currentState.copy();
			while(stateNew.shouldConstructRobot(2) && !stateNew.canConstruct(2) && stateNew.robots[0] > 0 && stateNew.robots[1] > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0];
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
			}
			if(stateNew.canConstruct(2) && stateNew.shouldConstructRobot(2) && stateNew.minutes > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] = stateNew.minerals[0] + stateNew.robots[0] - stateNew.blueprint.obsidianCostOre;
				stateNew.minerals[1] += stateNew.robots[1] - stateNew.blueprint.obsidianCostClay;
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
				stateNew.robots[2]++;
				queue.add(stateNew);
				//System.out.println("Bought obsidian with " + stateNew.minutes + " minutes left");
			}
			//buy clay robot
			stateNew = currentState.copy();
			while(stateNew.shouldConstructRobot(1) && !stateNew.canConstruct(1) && stateNew.robots[0] > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0];
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
			}
			if(stateNew.canConstruct(1) && stateNew.shouldConstructRobot(1) && stateNew.minutes > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0] - stateNew.blueprint.clayCostOre;
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
				stateNew.robots[1]++;
				queue.add(stateNew);
			}
			//buy ore robot
			stateNew = currentState.copy();
			while(stateNew.shouldConstructRobot(0) && !stateNew.canConstruct(0) && stateNew.robots[0] > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0];
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
			}
			if(stateNew.canConstruct(0) && stateNew.shouldConstructRobot(0) && stateNew.minutes > 0) {
				stateNew.minutes--;
				stateNew.minerals[0] += stateNew.robots[0] - stateNew.blueprint.oreCostOre;
				stateNew.minerals[1] += stateNew.robots[1];
				stateNew.minerals[2] += stateNew.robots[2];
				stateNew.minerals[3] += stateNew.robots[3];
				stateNew.robots[0]++;
				queue.add(stateNew);
			}
			//just collect until the end
			if(!stateNew.shouldConstructRobot(0) && !stateNew.shouldConstructRobot(1) && !stateNew.shouldConstructRobot(2)) {
				continue;
			}
			while(currentState.minutes > 0) {
				currentState.minutes--;
				currentState.minerals[0] += currentState.robots[0];
				currentState.minerals[1] += currentState.robots[1];
				currentState.minerals[2] += currentState.robots[2];
				currentState.minerals[3] += currentState.robots[3];				
				queue.add(currentState);
			}
		}
		return maxGeodes;
	}
}
