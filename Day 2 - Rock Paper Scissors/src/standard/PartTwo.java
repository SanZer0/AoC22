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
public class PartTwo {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		String rock = "X";
		String paper = "Y";
		String scissors = "Z";
		//create three arrays that have the shape to be chosen in order l - w - d
		String[] rockA = {scissors, rock, paper};
		String[] paperB = {rock, paper, scissors};
		String[] scissorsC = {paper, scissors, rock};
		String guide;
		int score = 0;
		while(sc.hasNextLine()) {
			guide = sc.nextLine().trim();
			String[] enemy_result = guide.split(" ");
			int choose = 0;
			switch(enemy_result[1]) {
				case "X": choose = 0;
							score += 0;
							break;
				case "Y": choose = 1;
							score += 3;
							break;
				case "Z": choose = 2;
							score += 6;
							break;
				default: break;
			}
			String shape = rock;
			switch (enemy_result[0]) {
				case "A": shape = rockA[choose];
									break;
				case "B": shape = paperB[choose];
									break;
				case "C": shape = scissorsC[choose];
									break;
				default: break;
			}
			if (shape == rock) {
				score += 1;
			} else if (shape == paper) {
				score += 2;
			} else if (shape == scissors) {
				score += 3;
			}
		}
		System.out.println("Your score is " + score + ".");
	}

}
