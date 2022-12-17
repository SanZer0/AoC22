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
		//find S and E
		int start_x = 0, start_y = 0;
		int goal_x = 0, goal_y = 0;
		for(int row = 0; row < heightMap.size(); row++) {
			for(int column = 0; column < heightMap.get(0).size(); column++) {
				if(heightMap.get(row).get(column).equals('S')) {
					start_x = row;
					start_y = column;
					System.out.println("Found S at " + column + " " + row);
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
		
		
		class Coords {
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
		//BFS to find the goal
		Queue<Coords> queue = new LinkedList<Coords>();
		queue.add(new Coords(start_x, start_y, 0, 'a'));
		while(!queue.isEmpty()) {
			visualize(heightMap);
			Coords currentLoc = queue.remove();
			if(currentLoc.x == goal_x && currentLoc.y == goal_y) {
				System.out.println("Reached goal in " + currentLoc.steps + " steps.");
				break;
			}
			Character newTile = null;
			//go to the right
			if(currentLoc.x + 1 < heightMap.size()) {				
				newTile = heightMap.get(currentLoc.x + 1).get(currentLoc.y);
				if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
				{ //tile is walkable
					queue.add(new Coords(currentLoc.x + 1, currentLoc.y, currentLoc.steps + 1, newTile));
					heightMap.get(currentLoc.x + 1).set(currentLoc.y, '|');
				}
			}
			
			//go to the left
			if(currentLoc.x - 1 > 0) {
				newTile = heightMap.get(currentLoc.x - 1).get(currentLoc.y);
				if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
				{ //tile is walkable
					queue.add(new Coords(currentLoc.x - 1, currentLoc.y, currentLoc.steps + 1, newTile));
					heightMap.get(currentLoc.x - 1).set(currentLoc.y, '|');
				}
			}
			
			//go top
			if(currentLoc.y + 1 < heightMap.get(0).size()) {
				newTile = heightMap.get(currentLoc.x).get(currentLoc.y + 1);
				if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
				{ //tile is walkable
					queue.add(new Coords(currentLoc.x, currentLoc.y + 1, currentLoc.steps + 1, newTile));
					heightMap.get(currentLoc.x).set(currentLoc.y + 1, '|');
				}				
			}
			
			//go down
			if(currentLoc.y - 1 > 0) {
				newTile = heightMap.get(currentLoc.x).get(currentLoc.y - 1);
				if((Character.valueOf(newTile)) - Character.valueOf(currentLoc.height) <= 1) 
				{ //tile is walkable
					queue.add(new Coords(currentLoc.x, currentLoc.y - 1, currentLoc.steps + 1, newTile));
					heightMap.get(currentLoc.x).set(currentLoc.y - 1, '|');
				}				
			}
		}
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
