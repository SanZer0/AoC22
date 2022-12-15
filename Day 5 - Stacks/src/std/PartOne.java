/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

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
		Stack<String> lines = new Stack<String>();
		while(sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if(nextLine.trim().isEmpty()) {
				break; //get out of the while after reading the first part of input
			}
			lines.push(nextLine);
		}
		String columns = lines.pop();
		
		//get the number of columns and their positions
		HashMap<Integer, Integer> columnPositions = new HashMap<>();
		int columnCount = 0;
		while(true) {		
			if(!columns.contains("" + (columnCount + 1))) {
				break;
			}
			columnCount++;
			columnPositions.put(columnCount, 1 + (columnCount-1) * 4);
		}
		
		List<Stack<Character>> stacks = new ArrayList<Stack<Character>>();
		for(int i = 0; i < columnCount; i++) {
			stacks.add(new Stack<Character>());
		}
		
		//fill the initial stacks
		while(!lines.isEmpty()) {
			String currentLine = lines.pop();
			for(int i = 0; i < columnCount; i++) {
				try {
					if(currentLine.charAt(columnPositions.get(i+1)) == ' ') {
						continue;
					}
					stacks.get(i).add(currentLine.charAt(columnPositions.get(i+1)));					
				} catch (StringIndexOutOfBoundsException e) {}
			}
		}
		
		printVisual(columnCount, stacks);
		
		//fill in the move-command lines
		LinkedList<String> moveLines = new LinkedList<>();
		while(sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if(nextLine.trim().isEmpty()) {
				break; //get out of the while after reading the first part of input
			}
			moveLines.add(nextLine);
		}
		
		//do the actions
		while(!moveLines.isEmpty()) {
			String[] command = moveLines.remove().split(" ");
			int amount = Integer.parseInt(command[1]);
			int from = Integer.parseInt(command[3]);
			int to = Integer.parseInt(command[5]);
			for(int i = 0; i < amount; i++) {
				stacks.get(to - 1).push(stacks.get(from - 1).pop());
			}
			printVisual(columnCount, stacks);
		}
		
		//get the solution
		for(int i = 0; i < columnCount; i++) {
			System.out.print(stacks.get(i).pop());
		}
		
		
		
		sc.close();
	}
	
	static void printVisual(int columnCount, List<Stack<Character>> stackies) {
		//copy this, else we'll change the list every time
		List<Stack<Character>> stacks = new ArrayList<>();
		stackies.toArray();
		for (Stack<Character> s: stackies) {
			stacks.add((Stack<Character>)s.clone());
		}
		//get the size of the highest stack
				int maxStackSize = 0;
				for(int i = 0; i < columnCount; i++) {
					if(maxStackSize < stacks.get(i).size()) {
						maxStackSize = stacks.get(i).size();				
					}
				}
				System.out.println("Highest Stack:" + maxStackSize);
				
				//print the initial visualization
				for(int j = maxStackSize; j > 0; j--) {
					for(int i = 0; i < columnCount; i++) {
						if(stacks.get(i).size() < j) {
							System.out.print("    ");
							continue;
						}
						System.out.print("[" + stacks.get(i).pop() + "] ");
					}			
					System.out.println();
				}
				for(int i = 0; i < columnCount; i++) {
					System.out.print(" "+ (i + 1) + "  ");
				}
				System.out.println();
	}

}
