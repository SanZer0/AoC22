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
		int topElves = 3;
		int[] mostCalories = new int[topElves];
		int[] elfToAsk = new int[topElves];
		while(sc.hasNextLine()) {
			//empty line means new elf
			String line = sc.nextLine().trim();
			if(line.isEmpty()) {
				//set the new elfToAsk if we found one with the most calories
				for (int i = 0; i < topElves; i++) {
					if (calorieCounter > mostCalories[i]) {
						//shift down the list after i
						for (int j = topElves - 1; j > i; j--) {
							elfToAsk[j] = elfToAsk[j - 1];
							mostCalories[j] = mostCalories[j - 1];
						}
						//change value from i'th elf
						elfToAsk[i] = elfCounter;
						mostCalories[i] = calorieCounter;
						break;
					}
				}
				elfCounter++;
				calorieCounter = 0;
			} 
			else { //count up the calories 
				calorieCounter += Integer.parseInt(line);
			}
		}
		int combinedCalories = 0;
		//print the top elves and accumulate the kcal
		for (int i = 0; i < topElves; i++) {
			System.out.println("The " + (i + 1) + ". elf to ask is #" + elfToAsk[i] + " with " + mostCalories[i] + " kcal.");
			combinedCalories += mostCalories[i];
		}
		System.out.println("The Top " + topElves + " elves have " + combinedCalories + " calories with them.");
	}

}
