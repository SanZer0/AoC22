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

	
	static int elementCount;
	static String input = "input.txt";
	static Node startNode = null;
	static final int DECRYPTIONKEY = 811589153;
	//static final int DECRYPTIONKEY = 1;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		readInput();
		//Observation: We're dealing with duplicates
		Node currentNode = startNode;
		for(int times = 0; times < 10; times++) {
			currentNode = startNode;
			for(int i = 0; i < elementCount; i++) {
				while(currentNode.getPosition() != i) {
					currentNode = currentNode.getNext();
				}
				//found the number
				if(currentNode.getValue() % (elementCount - 1) == 0) {
					continue;
				}
				//cut it out
				currentNode.getPrevious().setNext(currentNode.getNext());
				currentNode.getNext().setPrevious(currentNode.getPrevious());
				
				Node shiftingNode = currentNode;
				long number = shiftingNode.getValue();
				number = number % (elementCount - 1);
				if(number < 0) {
					number--;
				}
				while (number < 0) {
					  number += elementCount;
				}
				for(int j = 0; j < number; j++) {
					currentNode = currentNode.getNext();
				}
				Node next = currentNode.getNext();
				Node previous = currentNode;
				next.setPrevious(shiftingNode);
				previous.setNext(shiftingNode);
				shiftingNode.setPrevious(previous);
				shiftingNode.setNext(next);
			}
		}
			
		//search 0
		Node nod = startNode;
		while(nod.getValue() != 0) {
			nod = nod.getNext();
		}
		//now go through the list and find the numbers
		long sum = 0;
		currentNode = nod;
		for(int i = 1; i < 3001; i++) {
			currentNode = currentNode.getNext();
			if(i % 1000 == 0) {
				sum += currentNode.getValue();
				System.out.println(currentNode.getValue());
			}
		}
		System.out.println(sum);
		
	}
	
	public static void readInput() throws FileNotFoundException {
		File file = new File(input);
		Scanner sc = new Scanner(file);
		elementCount = 0;
		Node currentNode = null;
		int position = 0;
		while(sc.hasNextInt()) {
			long number = sc.nextInt();
			long next = number * DECRYPTIONKEY;
			Node newNode = new Node(next, position++);
			if(currentNode != null) {
				currentNode.setNext(newNode);				
			}
			newNode.setPrevious(currentNode);
			currentNode = newNode;
			if(startNode == null) {
				startNode = currentNode;
			}
			elementCount++;
		}
		currentNode.setNext(startNode);
		startNode.setPrevious(currentNode);
		sc.close();
	}
	
	
}
