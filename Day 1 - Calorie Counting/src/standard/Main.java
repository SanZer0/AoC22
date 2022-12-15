/**
 * 
 */
package standard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * @author SanZer0
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		int elfCounter = 1;
		int calorieCounter = 0;
		int mostCalories = 0;
		int elfToAsk;
		while(sc.hasNextLine()) {
			//empty line means new elf
			String line = sc.nextLine().trim();
			if(line.isEmpty()) {
				//set the new elfToAsk if we found one with the most calories
				if(calorieCounter > mostCalories) {
					elfToAsk = elfCounter;
					mostCalories = calorieCounter;
				}
				elfCounter++;
				calorieCounter = 0;
			} 
			else { //count up the calories 
				calorieCounter += Integer.parseInt(line);
			}
		}
		System.out.println("Elf " + elfCounter + " has the most candies with " + mostCalories + " kcal.");
	}

}
