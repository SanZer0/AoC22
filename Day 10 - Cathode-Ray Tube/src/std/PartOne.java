/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author jonas
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
		int cycle = 0;
		int progress = 0;
		int add = 0;
		int X = 1;
		int sumSignalStrength = 0;
		while(sc.hasNextLine()) {
			String[] command = sc.nextLine().trim().split(" ");
			if(command[0].equals("noop")) {
				//advance the tick
				progress = 1;
			} else {//command[0].equals("addx")
				progress = 2;
				add = Integer.parseInt(command[1]);
			}
			while(progress > 0) {
				//draw pixel
				cycle++;
				if(cycle % 40 == 1 && cycle != 1) {
					System.out.println();
				}
				if(cycle % 40 >= X && cycle % 40 <= X + 2) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
				
				progress--;
				if((cycle + 20) % 40 == 0) {
					//
					int signalStrength = cycle * X;
					//System.out.println(cycle +" * "+ X + " = " +signalStrength);
					sumSignalStrength += signalStrength;
				}
			}
			X += add;
			add = 0;
			if(cycle > 220) {
				//System.out.println("Finished with " + sumSignalStrength);
			}
		}
	}

}
