/**
 * 
 */
package std;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * @author SanZer0
 * 
 */
public class PartTwo {

	/**Given two elves with sections that overlap, find the number of full inclusions
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		int count = 0;
		while(sc.hasNextLine()) {
			//Get the ranges
			String[] sections = sc.nextLine().trim().split(",");
			String[] range1 = sections[0].split("-");
			String[] range2 = sections[1].split("-");
			int[] intRange1 = {Integer.parseInt(range1[0]), Integer.parseInt(range1[1])};
			int[] intRange2 = {Integer.parseInt(range2[0]), Integer.parseInt(range2[1])};
			//quite easy, just check single inclusion now
			if (intRange1[0] >= intRange2[0] && intRange1[0] <= intRange2[1]
					|| intRange1[1] >= intRange2[0] && intRange1[0] <= intRange2[1]
					|| intRange2[0] >= intRange1[0] && intRange2[0] <= intRange1[1]
					|| intRange2[1] >= intRange1[0] && intRange2[0] <= intRange1[1]) 
			{
				count++;
				System.out.println("Overlap found in " + sections[0] + "," + sections[1]);
			}
		}
		System.out.println(count + " ranges fully include their counterpart.");
		
		sc.close();

	}

}
