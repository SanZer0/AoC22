/**
 * 
 */
package std;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
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
	static long deletedLines = 0;
	static int highestRelativeRock = 0;
	final static int above = 3 + 1;//something needs to be cleaned again
	final static int right = 2;
	final static int width = 7;
	static List<String> chamber;
	static Rock currentRock;
	static long counter;
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
		counter = -1;
		while(counter < 1000000000000L) {
			if(stopped) {
				if(counter % 10000 == 0 && counter != 0) {
					System.out.println(counter);
				}
				stopped = false;
				counter++;
				spawnRock();
			}
			blowRock();
			fallRock();
		}
		visualizeChamber();
		System.out.println("Highest block is at " + (deletedLines + highestRelativeRock));

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
		currentRock = new Rock(nextRock, right, highestRelativeRock + above);
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
				if(p.y < 0 || p.y > highestRelativeRock) {continue;}
				if(chamber.get(p.y).charAt(p.x) == '#') { //next block is a rock
					return;
				}
			}
			currentRock.x--;
		} else if(wind == '>' && currentRock.xRange()[1] < width - 1) {
			//check for rocks
			for(Point p: coords) {
				//get the coordinate after the change, check if #
				if(p.y < 0 || p.y > highestRelativeRock) {continue;}
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
			if(p.y - 1 < 0 || p.y - 1 > highestRelativeRock) {continue;}
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
		for(int i = highestRelativeRock; i < rock.yRange()[1]; i++) {
			chamber.add("|" + ".".repeat(width) + "|");
		}
		//pretty inefficient, but will work
		for(Point p : rock.getCoords()) {
			char[] arr = chamber.get(p.y).toCharArray();
			arr[p.x + 1] = '#'; 
			chamber.set(p.y, String.valueOf(arr));
		}
		highestRelativeRock = chamber.size() - 1;
		deleteLines();
	}
	
	static List<String> cycleDect = null;
	static HashMap<String, Tuple<BigInteger, BigInteger>> map = null;
	private static void deleteLines() {
		if(cycleDect == null) {
			cycleDect = new ArrayList<String>();			
		}
		if(map == null) {
			map = new HashMap<String, Tuple<BigInteger, BigInteger>>();			
		}
		for(int i = 1; i < chamber.size(); i++) {
			if(chamber.get(i).equals("|" + "#".repeat(width) + "|")) { //line is full
				deletedLines += i;
				chamber = chamber.subList(i, chamber.size());
				highestRelativeRock -= i;
				//System.out.println("h:"+highestRelativeRock + " s:" + chamber.size() + " r:" +nextRock + " w:" + nextWind + " d:" + deletedLines + " c:" + counter);
				String cycleString = "h:"+highestRelativeRock + " s:" + chamber.size() + " r:" +nextRock + " w:" + nextWind;
				//cycle detection
				if(!cycleDect.contains(cycleString)) {
					cycleDect.add(cycleString);
					map.put(cycleString, new Tuple<BigInteger, BigInteger>(BigInteger.valueOf(deletedLines), BigInteger.valueOf(counter)));
				} else {
					long droppedRocks = counter - map.get(cycleString).y.longValue();
					long addedHeight = deletedLines - map.get(cycleString).x.longValue();
					//Mathe und Ende
					System.out.println("Im Cycle werden "+ droppedRocks + " Steine gedropped und es wird "+ addedHeight + " an Höhe addiert.");
					long remRocks = 1000000000000L - counter;
					long cyclesLeft = remRocks / droppedRocks;
					counter += cyclesLeft * droppedRocks;
					deletedLines += cyclesLeft * addedHeight;
					System.out.println("found a cycle");
					cycleDect = null;
				}
				break;
			}			
		}
	}
	public static void visualizeChamber() {
		for(int i = chamber.size()-1; i >= 0; i--) {
			System.out.println(chamber.get(i) + i);
		}
	}
}
