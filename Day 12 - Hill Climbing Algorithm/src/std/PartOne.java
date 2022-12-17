/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class PartOne {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		
		List<List<Character>> heightMap = new ArrayList<List<Character>>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			List<Character> currentLine = new ArrayList<Character>();
			heightMap.add(currentLine);
			for(Character a : line.toCharArray()) {
				currentLine.add(a);
			}
		}
		sc.close();
		
		//Coords class needed
		class Coords {
			public Coords(int x, int y) {
				this.x = x;
				this.y = y;
			}
			public Coords(int x, int y, int steps, Character height) {
				this.x = x;
				this.y = y;
				this.steps = steps;
				this.height = height;
			}
			public int x;
			public int y;
			public int steps;
			Character height;
		}
				
		//find S and E
		Queue<Coords> startList = new LinkedList<Coords>();
		int start_x = 0, start_y = 0;
		int goal_x = 0, goal_y = 0;
		for(int row = 0; row < heightMap.size(); row++) {
			for(int column = 0; column < heightMap.get(0).size(); column++) {
				if(heightMap.get(row).get(column).equals('a')) {
					Coords start = new Coords(row, column);
					startList.add(start);
					System.out.println("Found an a at " + column + " " + row);
				}
				if(heightMap.get(row).get(column).equals('E')) {
					goal_x = row;
					goal_y = column;
					System.out.println("Found E at " + column + " " + row);
				}
				//System.out.print(heightMap.get(row).get(column));
			}
			//System.out.println();
		}
		
		
		//BFS to find the goal
		int leastSteps = Integer.MAX_VALUE;
		while(!startList.isEmpty()) {
			System.out.println("Started new search");
			//copy the old list
			List<List<Character>> newHeightMap = new ArrayList<List<Character>>();
			for(List<Character> list: heightMap) {
				List<Character> newList = new ArrayList<Character>();
				for(Character a : list) {
					newList.add(a);
				}
				newHeightMap.add(newList);
				
			}
			Coords start = startList.remove();
			Queue<Coords> queue = new LinkedList<Coords>();
			queue.add(new Coords(start.x, start.y, 0, 'a'));
			while(!queue.isEmpty()) {
				//visualize(heightMap);
				Coords currentLoc = queue.remove();
				if(currentLoc.x == goal_x && currentLoc.y == goal_y) {
					System.out.println("Reached goal in " + currentLoc.steps + " steps.");
					if(currentLoc.steps < leastSteps) {
						leastSteps = currentLoc.steps;
					}
					break;
				}
				Character newTile = null;
				//go to the right
				if(currentLoc.x + 1 < newHeightMap.size()) {				
					newTile = newHeightMap.get(currentLoc.x + 1).get(currentLoc.y);
					if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
					{ //tile is walkable
						queue.add(new Coords(currentLoc.x + 1, currentLoc.y, currentLoc.steps + 1, newTile));
						newHeightMap.get(currentLoc.x + 1).set(currentLoc.y, '|');
					}
				}
				
				//go to the left
				if(currentLoc.x - 1 > 0) {
					newTile = newHeightMap.get(currentLoc.x - 1).get(currentLoc.y);
					if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
					{ //tile is walkable
						queue.add(new Coords(currentLoc.x - 1, currentLoc.y, currentLoc.steps + 1, newTile));
						newHeightMap.get(currentLoc.x - 1).set(currentLoc.y, '|');
					}
				}
				
				//go top
				if(currentLoc.y + 1 < newHeightMap.get(0).size()) {
					newTile = newHeightMap.get(currentLoc.x).get(currentLoc.y + 1);
					if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
					{ //tile is walkable
						queue.add(new Coords(currentLoc.x, currentLoc.y + 1, currentLoc.steps + 1, newTile));
						newHeightMap.get(currentLoc.x).set(currentLoc.y + 1, '|');
					}				
				}
				
				//go down
				if(currentLoc.y - 1 > 0) {
					newTile = newHeightMap.get(currentLoc.x).get(currentLoc.y - 1);
					if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
					{ //tile is walkable
						queue.add(new Coords(currentLoc.x, currentLoc.y - 1, currentLoc.steps + 1, newTile));
						newHeightMap.get(currentLoc.x).set(currentLoc.y - 1, '|');
					}				
				}
			}
		}
		System.out.println(leastSteps);
	}
	
	
	public static void visualize(List<List<Character>> heightMap) {
		for(int row = 0; row < heightMap.size(); row++) {
			for(int column = 0; column < heightMap.get(0).size(); column++) {
				System.out.print(heightMap.get(row).get(column));
			}
			System.out.println();
		}
	}
}
