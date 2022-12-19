/**
 * 
 */
package std;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {

	static boolean stopped = true;
	static String stream;
	final static String input = "input.txt";
	static int nextWind = 0;
	static int nextRock = 0;
	static int highestRock = 0;
	final static int above = 3 + 1;//something needs to be cleaned again
	final static int right = 2;
	final static int width = 7;
	static List<String> chamber;
	static Rock currentRock;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		getStream();
		//initialize the chamber
		chamber = new ArrayList<String>();
		chamber.add("+" + "-".repeat(width) + "+");
		int counter = -1;
		while(counter < 2022) {
			if(stopped) {
				stopped = false;
				counter++;
				spawnRock();
				System.out.println(currentRock.x);
			}
			blowRock();
			fallRock();
		}
		visualizeChamber();
		System.out.println("Highest block is at " + highestRock);

	}
	//write the input into the variable
	public static void getStream() throws FileNotFoundException {
		File file = new File(input);
		Scanner sc = new Scanner(file);
		String line = "";
		while(sc.hasNextLine()) {
			line = line.concat(sc.nextLine());
		}
		stream = line;
	}
	
	//spawn the next rock
	public static void spawnRock() {
		//spawn a new rock
		currentRock = new Rock(nextRock, right, highestRock + above);
		nextRock = (nextRock + 1) % 5;
		//that's it actually
	}
	
	//blow the rock away(if possible)
	public static void blowRock() {
		Character wind = stream.charAt(nextWind);
		//update for next iteration, loop via if
		nextWind++;
		if(nextWind == stream.length()) {
			nextWind = 0;
		}
		Point[] coords = currentRock.getCoords();
		//move the rock accordingly
		if(wind == '<' && currentRock.xRange()[0] != 0) {
			//check for rocks
			for(Point p: coords) {
				//get the coordinate after the change, check if #
				if(p.y < 0 || p.y > highestRock) {continue;}
				if(chamber.get(p.y).charAt(p.x) == '#') { //next block is a rock
					return;
				}
			}
			currentRock.x--;
		} else if(wind == '>' && currentRock.xRange()[1] < width - 1) {
			//check for rocks
			for(Point p: coords) {
				//get the coordinate after the change, check if #
				if(p.y < 0 || p.y > highestRock) {continue;}
				if(chamber.get(p.y).charAt(p.x+2) == '#') { //next block is a rock
					return;
				}
			}
			currentRock.x++;
		} else {
			return;
		}
		currentRock.updateCoords();
	}
	
	//let the rock fall. if it can't fall anymore, set stopped to true
	//and update the chamber
	public static boolean fallRock() {
		//check if it can fall
		for(Point p: currentRock.getCoords()) {
			//get the coordinate after the change, check for #
			if(p.y - 1 < 0 || p.y - 1 > highestRock) {continue;}
			char nextBlock = chamber.get(p.y - 1).charAt(p.x + 1);//go down, one to the right to acc. for barrier
			if(nextBlock == '#' || nextBlock == '-') { //next block is a rock
				stopped = true;
				updateChamber(currentRock);
				return !stopped;
			}
		}
		//no blocks below, so drop
		currentRock.y--;
		currentRock.updateCoords();
		return !stopped;
	}
	
	public static void updateChamber(Rock rock) {
		for(int i = highestRock; i < rock.yRange()[1]; i++) {
			chamber.add("|" + ".".repeat(width) + "|");
		}
		//pretty inefficient, but will work
		for(Point p : rock.getCoords()) {
			char[] arr = chamber.get(p.y).toCharArray();
			arr[p.x + 1] = '#'; 
			chamber.set(p.y, String.valueOf(arr));
		}
		highestRock = chamber.size() - 1;
	}
	
	public static void visualizeChamber() {
		for(int i = chamber.size()-1; i >= 0; i--) {
			System.out.println(chamber.get(i) + i);
		}
	}
}
