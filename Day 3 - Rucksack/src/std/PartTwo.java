/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * @author SanZer0
 *
 */
public class PartTwo {
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		
		int sum = 0;
		//assuming input is correctly divisible by 3
		while(sc.hasNextLine()) {
			String[] lines = new String[3];
			lines[0] = sc.nextLine();
			lines[1] = sc.nextLine();
			lines[2] = sc.nextLine();
			for (char letter: lines[0].toCharArray()) {
				if (lines[1].contains("" + letter)) {
					if (lines[2].contains("" + letter)) {
						int priority;
						if((int) letter > 96) { //lowercase 1 to 26
							priority = (int) letter -96;
						} else { //uppercase 27 to 52
							priority = (int) letter -64 + 26;
						}
						sum += priority;
						System.out.println(letter + " " + priority);
						break;						
					}
				}
			}
		}
		sc.close();
		System.out.println("Total priorities are " + sum);

	}

}
