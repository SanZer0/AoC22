/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jonas
 *
 */
public class PartOne {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		
		//fill in the big array
		List<List<Integer>> graph = new ArrayList<List<Integer>>();
		int row = 0;
		int noColumns = 0;
		//fill the first line and determine how many columns are needed
		if(sc.hasNextLine()) {
			String line = sc.nextLine();
			noColumns = line.length();
			for(int i = 0; i < noColumns; i++) {
				List<Integer> newList = new ArrayList<Integer>();
				newList.add(row, Integer.parseInt(String.valueOf(line.charAt(i))));
				graph.add(i, newList);
			}
		}
		//fill in the remaining lines and count the rows
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			row += 1;
			for(int i = 0; i < noColumns; i++) {
				graph.get(i).add(row, Integer.parseInt(String.valueOf(line.charAt(i))));				
			}
		}
		//print board
		
		for(int j = 0; j <= row; j++) {
			for(int i = 0; i < noColumns; i++) {
				System.out.print(graph.get(i).get(j));
			}
			System.out.println();
		}
		
		//now to the task
		//top side
		int treeCount = 0;
		for(int i = 0; i < noColumns; i++) {
			for(int j = 0; j <= row; j++) {
				//look at a single tree
				int treeHeight = graph.get(i).get(j);
				int seen = 4;
				for(int a = i + 1; a < noColumns; a++) {
					if(graph.get(a).get(j) >= treeHeight) {
						//vom baum nach rechts gehen, wird er verdeckt?
						seen--;
						break;
					}
				}
				for(int b = i - 1; b >= 0; b--) {
					if(graph.get(b).get(j) >= treeHeight) {
						//baum wird von hier nicht gesehen
						seen--;
						break;
					}
				}
				for(int c = j + 1; c <= row; c++) {
					if(graph.get(i).get(c) >= treeHeight) {
						//baum wird von hier nicht gesehen
						seen--;
						break;
					}
				}
				for(int d = j - 1; d >= 0; d--) {
					if(graph.get(i).get(d) >= treeHeight) {
						//baum wird von hier nicht gesehen
						seen--;
						break;
					}
				}
				if(seen > 0) {
					treeCount++;
				}
			}
		}
		System.out.println("Insgesamt " + treeCount + " Bäume werden gesehen");
		sc.close();
	}

}
