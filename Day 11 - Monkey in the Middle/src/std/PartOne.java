/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
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
		
		List<Monkey> monkeys = new LinkedList<Monkey>();
		Monkey currentMonkey = null;
		while(sc.hasNextLine()) {
			String[] line = sc.nextLine().trim().replace(":", "").replace(",", "").split(" ");
			/*for(int i = 0; i < line.length; i++) {
				System.out.print(line[i] + " ");				
			}
			System.out.println();*/
			if(line[0].equals("Monkey")) { //add a new monkey and remember it
				currentMonkey = new Monkey(monkeys);
				monkeys.add(Integer.parseInt(line[1]), currentMonkey);
			} else if(line[0].equals("Starting")) { //give the monkey it's items
				for(int i = 2; i < line.length; i++) {
					Item currentItem = new Item(Integer.parseInt(line[i]));
					currentMonkey.GiveItem(currentItem);
				}	
			} else if(line[0].equals("Operation")) {
				currentMonkey.SetOperation(Arrays.copyOfRange(line, 3, 6));
			} else if(line[0].equals("Test")) {
				currentMonkey.SetDivisor(Integer.parseInt(line[3]));
			} else if(line[0].equals("If")) {
				if(line[1].equals("true")) {
					currentMonkey.SetTrueThrow(Integer.parseInt(line[5]));
				} else if(line[1].equals("false")) {
					currentMonkey.SetFalseThrow(Integer.parseInt(line[5]));
				}
				
			}
		}
		sc.close();
		//do 20 rounds of this
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < monkeys.size(); j++) {
				Monkey current = monkeys.get(j);
				while(current.HasItems()) {
					current.ThrowItem();
				}
			}
			//after each round print the monkeys:
			for(int j = 0; j < monkeys.size(); j++) {
				System.out.print("Monkey "+ j + ": ");
				Queue<Item> queue = monkeys.get(j).GetItems();
				for(int k = 0; k < queue.size(); k++) {
					System.out.print(queue.remove().GetWorryLevel() + " ");
				}
				System.out.println();
			}
		}
		int highest = 0;
		int secondHighest = 0;
		for(int i = 0; i < monkeys.size(); i++) {
			Monkey monkey = monkeys.get(i);
			int itemCount = monkey.GetInspected();
			System.out.println("Monkey " + i +  " inspected " + itemCount + " items.");
			if(itemCount > highest) {
				secondHighest = highest;
				highest = itemCount;
			} else if(itemCount > secondHighest) {
				secondHighest = itemCount;
			}
		}
		System.out.println("Highest: " + highest + "  Second highest: " + secondHighest);
		System.out.println("Monkey business of " + (highest * secondHighest));
	}

}
