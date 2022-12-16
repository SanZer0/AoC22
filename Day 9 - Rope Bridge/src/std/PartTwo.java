/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author jonas
 *
 */
public class PartTwo {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		int[] head = {0, 0};
		//we need more tails now
		int tailCount = 9;
		int[][] tails = new int[tailCount][2];
		for(int i = 0; i < 9; i++) {
			tails[i][0] = 0;
			tails[i][1] = 0;
		}
		List<String> visitedPositions = new ArrayList<String>(); 
		visitedPositions.add("0 0");
		boolean inMovement = false;
		//get the new head position
		while(sc.hasNextLine()) {		
			String[] command = sc.nextLine().trim().split(" ");
			String direction = command[0];
			int steps = Integer.parseInt(command[1]);
			inMovement = true;
			while(inMovement) {//don't read a new line unitl movement is finished
				if(steps == 0) {
					inMovement = false;
					continue;
				}
				switch(command[0]) {
				case "R": head[0]++;
				break;
				case "L": head[0]--;
				break;
				case "U": head[1]++;
				break;
				case "D": head[1]--;
				break;
				default: break;
				}
				steps--;
				//if the tail isn't touching the head
				for(int i = 0; i < tailCount; i++) {
					int x_diff, y_diff;
					if(i == 0) {//first one looks at head
						x_diff = tails[i][0] - head[0];
						y_diff = tails[i][1] - head[1];						
					} else { //others look at predecessor
						x_diff = tails[i][0] - tails[i - 1][0];
						y_diff = tails[i][1] - tails[i - 1][1];						
					}
					int sign_x = 0;
					int sign_y = 0;
					if(norm(x_diff) >= 2) { //in x direction
						sign_x = (int) Math.signum(x_diff);
						if(norm(y_diff) > 0) {
							sign_y = (int) Math.signum(y_diff);
						}
					}
					else if (norm(y_diff) >= 2) {//in y direction
						sign_y = (int) Math.signum(y_diff);
						if(norm(x_diff) > 0) {
							sign_x = (int) Math.signum(x_diff);
						}
					}
					
					//change the tail's position
					tails[i][0] -= sign_x;
					tails[i][1] -= sign_y;	
					if(i == tailCount -1) {
						//save the new position (if something changed)
						if (sign_x != 0 || sign_y != 0) {
							String newPos = tails[i][0] + " " + tails[i][1];
							if(!visitedPositions.contains(newPos)) {
								visitedPositions.add(newPos);					
							}
						}						
					}
				}
				
				//visualizeHeadArea(head, tail);
			}
		}		
		System.out.println("Rope visited " + visitedPositions.size() + " tiles.");
		sc.close();
		
	}
	
	
	//just a simple norm
	public static int norm(int a) {
		if(a < 0) {
			return a * -1;
		}
		return a;
	}
	
	public static void visualizeHeadArea(int[] head, int[] tail) throws InterruptedException {
		for(int i = head[0] - 2; i <= head[0] + 2; i++) {
			for(int j = head[1] - 2; j <= head[1] + 2; j++) {
				if(i == head[0] && j == head[1]) {
					System.out.print("H");
				} else {
					if(i == tail[0] && j == tail[1]) {
						System.out.print("T");						
					} else {
						System.out.print(".");
					}
				}
			}
			System.out.println(); 
		}
		System.out.println();
	}
}
