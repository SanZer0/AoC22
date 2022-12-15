/**
 * 
 */
package standard;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
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
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("A X", 3 + 1);
		hm.put("A Y", 6 + 2);
		hm.put("A Z", 0 + 3);
		hm.put("B X", 0 + 1);
		hm.put("B Y", 3 + 2);
		hm.put("B Z", 6 + 3);
		hm.put("C X", 6 + 1);
		hm.put("C Y", 0 + 2);
		hm.put("C Z", 3 + 3);
		String guide;
		int score = 0;
		while(sc.hasNextLine()) {
			guide = sc.nextLine().trim();
			score += hm.get(guide);
		}
		System.out.println("Your score is " + score + ".");
	}

}
