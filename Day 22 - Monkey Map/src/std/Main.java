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
				drawMap();
			}
			commandPos++;
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
		if(y < 0) {
			y = map.size() - 1;
		}
		if(y > map.size() - 1) {
			y = 0;
		}
		String line = map.get(y);
		int lineLength = line.length();
		//OutofBounds-Check
		System.out.println("Moving to x " + x);
		if(rotation == 2 && (x < 0 || map.get(y).charAt(x) == ' ')) {
			int linePos = lineLength - 1;
			while(line.charAt(linePos) == ' ') {
				linePos--;
			}
			x = linePos;
		}
		if(rotation == 0 && (x > lineLength - 1 || map.get(y).charAt(x) == ' ')) {
			int linePos = 0;
			while(line.charAt(linePos) == ' ') {
				linePos++;
			}
			x = linePos;
		}
		int lineAmount = map.size();
		if(rotation == 3 && (y < 0 || x > map.get(y).length() || map.get(y).charAt(x) == ' ')) {
			int linePos = lineAmount - 1;
			while(map.get(linePos).length() < x || map.get(linePos).charAt(x) == ' ') {
				linePos--;
			}
			y = linePos;
			System.out.println("Y is " + y);
		}
		if(rotation == 1 && (y > lineAmount - 1 || x > map.get(y).length() || map.get(y).charAt(x) == ' ')) {
			int linePos = 0;
			while(map.get(linePos).charAt(x) == ' ') {
				linePos++;
			}
			y = linePos;
		}
		if(map.get(y).charAt(x) == '#') {
			//don't move
			return false;
		} else if (map.get(y).charAt(x) == '.') {
			posX = x;
			posY = y;
			System.out.println("Moved to X: " + x + " Y: " + y);
			 return true;
		} else {
			System.out.println("Error: something went wrong with moving");
			System.out.println(map.get(y).charAt(x));
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
