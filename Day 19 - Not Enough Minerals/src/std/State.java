/**
 * 
 */
package std;

import java.util.Arrays;

/**
 * @author SanZer0
 *
 */
public class State {

	Blueprint blueprint;
	int minutes;
	int[] robots;
	int[] minerals;
	int[] maximumRobots;
	/**
	 * 
	 */
	public State(Blueprint blueprint, int minutes) {
		this.blueprint = blueprint;
		this.minutes = minutes;
		robots = new int[4];
		robots[0] = 1;
		robots[1] = 0;
		robots[2] = 0;
		robots[3] = 0;
		minerals = new int[4];
		
		computeMaximumRobots();
		
	}
	
	public State(Blueprint blueprint, int minute, int[] robots, int[] minerals) {
		this(blueprint, minute);
		for(int i = 0; i < 4; i++) {
			this.robots[i] = robots[i];
			this.minerals[i] = minerals[i];
		}
		
	}
	
	public void nextMinute() {
		minutes--;
		for(int i = 0; i < 4; i++) {
			minerals[i] += robots[i];
		}
	}
	
	public void runOutTime() {
		while(minutes > 0) {
			nextMinute();
		}
	}
	
	public int getGeodes() {
		return minerals[3];
	}
	
	public boolean canConstruct(int robot) {
		if(robot == 0 && blueprint.oreCostOre <= minerals[0]) {
			return true;
		}
		if(robot == 1 && blueprint.clayCostOre <= minerals[0]) {
			return true;
		}
		if(robot == 2 && blueprint.obsidianCostOre <= minerals[0] && blueprint.obsidianCostClay <= minerals[1]) {
			return true;
		}
		if(robot == 3 && blueprint.geodeCostOre <= minerals[0] && blueprint.geodeCostObsidian <= minerals[2]) {
			return true;
		}
		return false;
	}
	
	public boolean constructRobot(int robot) {
		//System.out.println("Yo what");
		int oreNeeded = (robot == 0) ? blueprint.oreCostOre : (robot == 1) ? blueprint.clayCostOre: 
						(robot == 2) ? blueprint.obsidianCostOre : blueprint.geodeCostOre;
		int clayNeeded = (robot == 2) ? blueprint.obsidianCostClay : 0;
		int obsidianNeeded = (robot == 3) ? blueprint.geodeCostObsidian : 0;
		if(oreNeeded != 0 && robots[0] == 0 || clayNeeded != 0 && robots[1] == 0 || obsidianNeeded != 0 && robots[2] == 0) {
			return false;
		}
		while(oreNeeded > minerals[0] && clayNeeded > minerals[1] && obsidianNeeded > minerals[2] && minutes > 0) {
			nextMinute();
		}
		if(minutes > 0) {
			robots[robot]++;
			nextMinute();
			return true;
		}
		return false;
	}
	
	public State copy() {
		return new State(blueprint, minutes, robots, minerals);
	}
	
	public int[] getRobots() {
		int[] copiedRobots = new int[4];
		System.arraycopy(robots, 0, copiedRobots, 0, robots.length);
		return copiedRobots;
	}
	
	public int[] getMinerals() {
		int[] copiedMinerals = new int[4];
		System.arraycopy(minerals, 0, copiedMinerals, 0, minerals.length);
		return copiedMinerals;
	}
	
	public void computeMaximumRobots() {
		int oreMax = Math.max(blueprint.oreCostOre, Math.max(blueprint.clayCostOre, Math.max(blueprint.obsidianCostOre, blueprint.geodeCostOre)));
		int clayMax = blueprint.obsidianCostClay;
		int obsidianMax = blueprint.geodeCostObsidian;
		int[] maximum = {oreMax, clayMax, obsidianMax};
		this.maximumRobots = maximum;
	}
	
	public int[] getMaximumRobots() {
		int[] copy = new int[3];
		System.arraycopy(maximumRobots, 0, copy, 0, maximumRobots.length);
		return copy;
	}
	
	public boolean shouldConstructRobot(int robot) {
		if(maximumRobots[robot] <= robots[robot]) {
			return false;
		}
		return true;
	}
	
	//Equals function
	public boolean equals(Object o) {
		if(!(o instanceof State)) {
			return false;
		}
		State comp = (State) o;
		if(this.blueprint.blueprintNumber != comp.blueprint.blueprintNumber) {
			return false;
		}
		for(int i = 0; i < 4; i++) {
			if(this.robots[i] != comp.robots[i]) {
				return false;
			}
			if(this.minerals[i] != comp.minerals[i]) {
				return false;
			}
		}
		if(this.minutes != comp.minutes) {
			return false;
		}
		System.out.println("HEREEEEEEEEEEEEE");
		return true;
	}

}
