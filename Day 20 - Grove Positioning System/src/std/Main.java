/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author SanZer0
 *
 */
public class Main {

	
	static List<Integer> numbers;
	static String input = "input.txt";
	static Node startNode = null;
	static Node zeroNode = null;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		readInput();
		//Observation: We're dealing with duplicates
		Node currentNode = startNode;
		for(int i = 0; i < numbers.size(); i++) {
			while(currentNode.getPosition() != i) {
				currentNode = currentNode.getNext();
			}
			//found the number
			if(currentNode.getValue() == numbers.size() || currentNode.getValue() == 0) {
				continue;
			}
			//cut it out
			currentNode.getPrevious().setNext(currentNode.getNext());
			currentNode.getNext().setPrevious(currentNode.getPrevious());
			
			Node shiftingNode = currentNode;
			int number = shiftingNode.getValue();
			if(number < 0) {
				for(int j = 0; j < Math.abs(number); j++) {
					currentNode = currentNode.getPrevious();
				}
				Node next = currentNode;
				Node previous = currentNode.getPrevious();
				next.setPrevious(shiftingNode);
				previous.setNext(shiftingNode);
				shiftingNode.setPrevious(previous);
				shiftingNode.setNext(next);
				System.out.println("Putting " + shiftingNode.getValue() + " between " + previous.getValue() + " and " + next.getValue());
			} else {
				for(int j = 0; j < number; j++) {
					currentNode = currentNode.getNext();
				}
				//got to the position where we want to insert
				Node next = currentNode.getNext();
				Node previous = currentNode;
				next.setPrevious(shiftingNode);
				previous.setNext(shiftingNode);
				shiftingNode.setPrevious(previous);
				shiftingNode.setNext(next);
				System.out.println("Putting " + shiftingNode.getValue() + " between " + previous.getValue() + " and " + next.getValue());				
			}
		}
		//now go through the list and find the numbers
		currentNode = zeroNode;
		int sum = 0;
		for(int i = 1; i < 3001; i++) {
			currentNode = currentNode.getNext();
			if(i % 1000 == 0) {
				sum += currentNode.getValue();
			}
		}
		System.out.println(sum);
		
	}
	
	public static void readInput() throws FileNotFoundException {
		File file = new File(input);
		Scanner sc = new Scanner(file);
		numbers = new ArrayList<Integer>();
		Node currentNode = null;
		int position = 0;
		while(sc.hasNextInt()) {
			int next = sc.nextInt();
			numbers.add(next);
			Node newNode = new Node(next, position++);
			if(currentNode != null) {
				currentNode.setNext(newNode);				
			}
			newNode.setPrevious(currentNode);
			currentNode = newNode;
			if(startNode == null) {
				startNode = currentNode;
			}
			if(currentNode.getValue() == 0) {
				zeroNode = currentNode;
			}
		}
		currentNode.setNext(startNode);
		startNode.setPrevious(currentNode);
		sc.close();
	}
	
	
}
