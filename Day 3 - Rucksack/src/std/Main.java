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
public class Main {
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		
		int sum = 0;
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String comp1 = line.substring(0, line.length()/2);
			String comp2 = line.substring(line.length()/2, line.length());
			for (char letter: comp1.toCharArray()) {
				if (comp2.contains("" + letter)) {
					int priority;
					if((int) letter > 96) {
						priority = (int) letter -96;
					} else {
						priority = (int) letter -64 + 26;
					}
					sum += priority;
					System.out.println(letter + " " + priority);
					break;
				}
			}
		}
		System.out.println("Total priorities are " + sum);

	}

}
