/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * @author SanZer0
 *
 */
public class main {

	static Tile[][][] maps;
	static int lineCount = 0;
	static int lineLength = 0;
	static int loopSize = 0;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		//idea: create an array of maxX * maxY * (maxX * maxY),
		//precompute all blizzard combinations
		//then just do bfs to find the exit.
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		lineCount = 0;
		lineLength = -1;
		while(sc.hasNextLine()) {
			lineCount++;
			if(lineLength == -1) {
				lineLength = sc.nextLine().length();
			} else {
				sc.nextLine();
			}
		}
		sc.close();
		System.out.println("File has " + lineCount + " lines with length " + lineLength);
		loopSize = (lineCount - 2) * (lineLength - 2);
		maps = new Tile[loopSize][lineCount][lineLength];
		sc = new Scanner(file);
		int currentLine = 0;
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			for(int i = 0; i < lineLength; i ++) {
				maps[0][currentLine][i] = new Tile(line.charAt(i)); 			
			}
			currentLine++;
		}
		sc.close();
		computeMaps();
		String first = printMap(0);
		System.out.println(printMap(0));
		for(int i = 1; i < loopSize ; i++) {
			String comp = printMap(i);
			//System.out.println(comp);
			if(first.equals(comp)) {
				System.out.println("Found loop at " + i);
				loopSize = i; 
				break;
			}
		}
		
		System.out.println(findLeastStepsWithBFS());

	}
	
	public static String printMap(int number) {
		String mapString = "";
		for(int i = 0; i < lineCount; i++) {
			for(int j = 0; j < lineLength ; j++) {
				mapString += maps[number][i][j];
			}
			mapString += "\n";
		}
		return mapString;
	}
	
	public static void computeMaps() {
		//calculate the next map
		for(int i = 0; i < loopSize - 1; i++) {
			for(int row = 0; row < lineCount; row++) {
				for(int column = 0; column < lineLength; column++) {
					//right 0, down 1, left 2, up 3
					maps[i+1][row][column] = new Tile('.');
					Tile currentTile = maps[i+1][row][column];
					if(maps[i][row][column].isWall()) {
						currentTile.setWall(1);
						continue;
					}
					if(row == 0 || column == 0 || row == lineCount -1 || column == lineLength -1) {continue;}
					if(maps[i][row][column - 1].hasBlizzard(0) || (column == 1 && maps[i][row][lineLength - 2].hasBlizzard(0))) {
						currentTile.setBlizzard(0,  1);
					}
					if(maps[i][row - 1][column].hasBlizzard(1) || (row == 1 && maps[i][lineCount - 2][column].hasBlizzard(1))) {
						currentTile.setBlizzard(1,  1);
					}
					if(maps[i][row][column + 1].hasBlizzard(2) || (column == lineLength - 2 && maps[i][row][1].hasBlizzard(2))) {
						currentTile.setBlizzard(2,  1);
					}
					if(maps[i][row + 1][column].hasBlizzard(3) || (row == lineCount - 2 && maps[i][1][column].hasBlizzard(3))) {
						currentTile.setBlizzard(3,  1);
					}
				}
			}
		}
	}
	
	public static int findLeastStepsWithBFS() {
		int currentX = 1;
		int currentY = 0;
		int currentMap = 0;
		int currentMinutes = 0;
		Set<State> visited = new HashSet<>();
		Queue<State> bfsQueue = new LinkedList<>();
		State current = new State(currentX, currentY, currentMap, currentMinutes);
		visited.add(current);
		bfsQueue.add(current);
		while(!bfsQueue.isEmpty()) {
			//System.out.println(visited.size() + " " + bfsQueue.size());
			current = bfsQueue.remove();
			currentX = current.x;
			currentY = current.y;
			currentMinutes = current.minutes;
			currentMap = current.mapNumber;
			int nextMap = currentMap + 1;
			if(currentMap == loopSize - 1) {
				nextMap = 0;
			}
			
			//check all the 5 positions if we can move there
			if(maps[nextMap][currentY][currentX].canBeMovedOn()) {
				State nextState = new State(currentX, currentY, nextMap, currentMinutes + 1);
				if(!visited.contains(nextState)) {
					visited.add(nextState);
					bfsQueue.add(nextState);
				}
			}
			if(currentY != 0 && maps[nextMap][currentY - 1][currentX].canBeMovedOn()) {//go up
				State nextState = new State(currentX, currentY - 1, nextMap, currentMinutes + 1);
				if(!visited.contains(nextState)) {
					visited.add(nextState);
					bfsQueue.add(nextState);
				}
			}
			if(maps[nextMap][currentY + 1][currentX].canBeMovedOn()) {//go down
				if(currentX == lineLength - 2 && currentY + 1 == lineCount - 1) {
					return currentMinutes + 1;
				}
				State nextState = new State(currentX, currentY + 1, nextMap, currentMinutes + 1);
				if(!visited.contains(nextState)) {
					visited.add(nextState);
					bfsQueue.add(nextState);
				}
			}
			if(maps[nextMap][currentY][currentX - 1].canBeMovedOn()) {//go left
				State nextState = new State(currentX - 1, currentY, nextMap, currentMinutes + 1);
				if(!visited.contains(nextState)) {
					visited.add(nextState);
					bfsQueue.add(nextState);
				}
			}
			if(maps[nextMap][currentY][currentX + 1].canBeMovedOn()) {//go right
				State nextState = new State(currentX + 1, currentY, nextMap, currentMinutes + 1);
				if(!visited.contains(nextState)) {
					visited.add(nextState);
					bfsQueue.add(nextState);
				}
			}
		}
		return 0;
	}

}
