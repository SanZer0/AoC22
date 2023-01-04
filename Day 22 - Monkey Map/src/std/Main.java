/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {

	
	static int posX = 0;
	static int posY = 0;
	static int rotation = 0;
	static List<String> map;
	static List<String> mapCopy;
	static String commands;
	static int commandPos = 0;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		commands = null;
		map = new ArrayList<String>();
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			if(line.isBlank()) {
				break;
			}
			map.add(line);
		}
		System.out.println(map.size());
		while(sc.hasNextLine()) {
			commands = sc.nextLine();
		}
		sc.close();
		mapCopy = new ArrayList<>(map);
		//find the start position
		posX = map.get(0).indexOf(".");
		while(commandPos < commands.length()) {
			if(commands.charAt(commandPos) == 'L') {
				rotate('L');
			} else if (commands.charAt(commandPos) == 'R') {
				rotate('R');
			} else {
				String number = "";
				while(commandPos != commands.length() && commands.charAt(commandPos) != 'L' && commands.charAt(commandPos) != 'R') {
					number += commands.charAt(commandPos);
					commandPos++;
				}
				int numberC = Integer.parseInt(number);
				System.out.println("Now moving " + numberC + " times");
				while(numberC > 0) {
					numberC--;
					if(!move()) {
						System.out.println("Hit a wall");
						break;
					}
				}
				commandPos--;
				System.out.println(commandPos);
				//drawMap();
			}
			commandPos++;
			drawMap();
		}
		System.out.println("Final Row: " + (posY + 1) + " Final Column: " + (posX + 1) + " Rot: " + rotation);
		System.out.println((posX+1) * 4 + (posY + 1) * 1000 + rotation);
	}
	
	//Move the character forward
	public static boolean move() {
		String visual = mapCopy.get(posY);
		char[] visualArr = visual.toCharArray();
		visualArr[posX] = (rotation == 0) ? '>' : (rotation == 1) ? 'v' : (rotation == 2) ? '<' : '^';
		mapCopy.set(posY, String.valueOf(visualArr));
		switch(rotation) {
			case 0: //right
				return stepOn(posX + 1, posY);
			case 1://down
				return stepOn(posX, posY + 1);
			case 2://left
				return stepOn(posX - 1, posY);
			case 3://up
				return stepOn(posX, posY - 1);
			default:
				return false;
		}
	}
	
	public static boolean stepOn(int x, int y) {
		//let's hardcode
		int tempRotation = rotation;
		if(y == -1 && x >= 50 && x <= 99 && rotation == 3) { //1
			y = x + 100;
			x = 0;
			tempRotation = 0;
		}
		if(y == -1 && x >= 100 && rotation == 3) { //2
			x = x-100;
			y = 199;
			tempRotation = 3;
		}
		if(x == 150 && y < 50 && rotation == 0) { //3
			x = 99;
			y = (49 - y) + 100;
			tempRotation = 2;
		}
		if(x >= 100 && x < 150 && y == 50 && rotation == 1) {//4
			y = x - 50;
			x = 99;
			tempRotation = 2;
		}
		if(x == 100 && y >= 50 && y < 100 && rotation == 0) {//5
			x = y + 50;
			y = 49;
			tempRotation = 3;
		}
		if(x == 100 && y >= 100 && y < 150 && rotation == 0) {//6
			x = 149;
			y = 49 - (y - 100);
			tempRotation = 2;
		}
		if(y == 150 && x >= 50 && x < 100 && rotation == 1) {//7
			y = x - 50 + 150;
			x = 49;
			tempRotation = 2;
		}
		if(x == 50 && y >= 150 && rotation == 0) {//8
			x = y - 150 + 50;
			y = 149;
			tempRotation = 3;
		}
		if(y == 200 && x < 50 && rotation == 1) {//9
			x = x + 100;
			y = 0;
			tempRotation = 1;
		}
		if(x == -1 && y >= 150 && rotation == 2) {//10
			x = y - 150 + 50;
			y = 0;
			tempRotation = 1;
		}
		if(x == -1 && y >= 100 && y < 150 && rotation == 2) {//11
			x = 50;
			y = 49 - (y - 100);
			tempRotation = 0;
		}
		if(y == 99 && x < 50 && rotation == 3) {//12
			y = x + 50;
			x = 50;
			tempRotation = 0;
		}
		if(x == 49 && y >= 50 && y < 100 && rotation == 2) {//13
			x = y - 50;
			y = 100;
			tempRotation = 1;
		}
		if(x == 49 && y < 50 && rotation == 2) {//14
			x = 0;
			y = (49 - y) + 100;
			tempRotation = 0;
		}
		
		if(map.get(y).charAt(x) == '#') {
			//don't move
			return false;
		} else if (map.get(y).charAt(x) == '.') {
			posX = x;
			posY = y;
			rotation = tempRotation;
			System.out.println("Moved to X: " + x + " Y: " + y);
			 return true;
		} else {
			System.out.println("Error: something went wrong with moving");
			System.out.println("Tried moving to X: " + x + " Y: " + y + " from X: " + posX + " Y: " +  posY);
			System.exit(1);
			return true;
		}
	}
	public static void rotate(char rot) {
		switch(rot) {
			case 'L': rotation = (rotation - 1) % 4;
			if(rotation < 0) {
				rotation += 4;
			}
			System.out.println("Turning left");
				break;
			case 'R': rotation = (rotation + 1) % 4;
			System.out.println("Turning right");
				break;
			default: 
				System.out.println("Wrong rotation");
				break;
		}
	}
	
	public static void drawMap() {
		for(String line : mapCopy) {
			System.out.println(line);
		}
	}
}
