/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author jonas
 *
 */
public class Main {

	static List<Coordinate> elvePositions;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		int y = 0;
		elvePositions = new ArrayList<>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			for(int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == '#') {
					elvePositions.add(new Coordinate(i, y));
				}
			}
			y++;
		}
		sc.close();
		elvePositions.forEach(a -> System.out.println(a));
		int direction = 0;
		for(int i = 0; i < 10; i++) {
			drawMap();
			moveElves(direction);
			direction = (direction + 1) % 4;
			System.out.println("New positions:");
			elvePositions.forEach(a -> System.out.println(a));
		}
		drawMap();
		System.out.println(getBlankSquares());
	}
	
	public static void moveElves(int direction) {
		HashMap<Coordinate, Coordinate> moveTo = new HashMap<>();
		elvePositions.forEach(coords -> {
				boolean[] proposeDirection = new boolean[4];
				int x = coords.x;
				int y = coords.y;
				//check all directions, this returns at most one true
				proposeDirection = checkDirections(direction, x, y);
				int currDirection = direction;
				for(int i = 0; i < 4; i++) {
					//Found the position he wants to move to
					if(proposeDirection[currDirection]) {
						Coordinate newCoords = null;
						switch(currDirection) {
							case 0://north
								newCoords = new Coordinate(coords.getX(), coords.getY() - 1);
								break;
							case 1://south
								newCoords = new Coordinate(coords.getX(), coords.getY() + 1);
								break;
							case 2://west
								newCoords = new Coordinate(coords.getX() - 1, coords.getY());
								break;
							case 3://east
								newCoords = new Coordinate(coords.getX() + 1, coords.getY());
								break;
						}
						moveTo.put(coords, newCoords);
						break;
					}
					currDirection = (currDirection + 1) % 4;
				}
			}
		);
		
		Collection<Coordinate> values = moveTo.values();
		moveTo.forEach((oldCoords, newCoords) -> {
			System.out.println(oldCoords + " moved to " + newCoords);
			if(Collections.frequency(values, newCoords) > 1) {
				System.out.println("Multiple elves try to move to " + newCoords);
			} else {
				Coordinate elve = elvePositions.get(elvePositions.indexOf(oldCoords));
				elve.setX(newCoords.getX());
				elve.setY(newCoords.getY());
			}
		});
	}
	
	public static boolean[] checkDirections(int direction, int x, int y) {
		Coordinate check1 = null;
		Coordinate check2 = null;
		Coordinate check3 = null;
		boolean[] directionCheck = new boolean[4];
		for(int i = 0; i < 4; i++) {
			switch(direction) {
				case 0: //north
					check1 = new Coordinate(x - 1, y - 1);
					check2 = new Coordinate(x, y - 1);
					check3 = new Coordinate(x + 1, y - 1);
					break;
				case 1: //south
					check1 = new Coordinate(x - 1, y + 1);
					check2 = new Coordinate(x, y + 1);
					check3 = new Coordinate(x + 1, y + 1);
					break;
				case 2: //west
					check1 = new Coordinate(x - 1, y - 1);
					check2 = new Coordinate(x - 1, y);
					check3 = new Coordinate(x - 1, y + 1);
					break;
				case 3: //east
					check1 = new Coordinate(x + 1, y - 1);
					check2 = new Coordinate(x + 1, y);
					check3 = new Coordinate(x + 1, y + 1);
					break;
				default: break;
			}
			if(elvePositions.contains(check1) || elvePositions.contains(check2) || elvePositions.contains(check3)) {
				directionCheck[direction] = false;
			} else {
				directionCheck[direction] = true;
			}
			direction = (direction + 1) % 4;
		}
		int proposeCount = 0;
		for(int i = 0; i < 4; i++) {
			if(directionCheck[direction]) {
				if(i > 0 && proposeCount > 0) {
					directionCheck[direction] = false;
				}
				proposeCount++;
			}
			direction = (direction + 1) % 4;
		}
		if(proposeCount == 4) {
			directionCheck[direction] = false;
		}
		return directionCheck;
	}
	
	public static int getBlankSquares() {
		int blank = 0;
		int[] box = getBoundingBox();
		for(int x = box[1]; x <= box[0]; x++) {
			for(int y = box[3]; y <= box[2]; y++) {
				if(!elvePositions.contains(new Coordinate(x, y))) {
					blank++;
				}
			}
		}
		return blank;
	}
	
	public static int[] getBoundingBox() {
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		for(int i = 0; i < elvePositions.size(); i++) {
			Coordinate coord = elvePositions.get(i);
			maxX = Math.max(maxX, coord.getX());
			minX = Math.min(minX, coord.getX());
			maxY = Math.max(maxY, coord.getY());
			minY = Math.min(minY, coord.getY());
		}
		int[] boundingBox = {maxX, minX, maxY, minY};
		return boundingBox;
	}
	
	public static void drawMap() {
		int[] box = getBoundingBox();
		for(int y = box[3]; y <= box[2]; y++) {
			for(int x = box[1]; x <= box[0]; x++) {
				if(!elvePositions.contains(new Coordinate(x, y))) {
					System.out.print(".");
				} else {
					System.out.print("#");
				}
			}
			System.out.println();
		}
	}
}
