/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

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
		String line1 = null;
		String line2 = null;
		int indices = 0;
		int indicesSum = 0;
		int inOrder = 0;
		int outOfOrder = 0;
		int noDecision = 0;
		while(sc.hasNextLine()) {
			String nextLine = sc.nextLine();
			if(nextLine.trim().isEmpty()) { //announcement of a new pair
				line1 = null;
				line2 = null;
			} else if(line1 == null) {//package1
				line1 = nextLine;
			} else if(line2 == null) {//package2
				line2 = nextLine;
				indices++;
			}
			//we don't have two packets to compare yet
			if(line2 == null) {
				continue;
			}
			System.out.println(line1);
			System.out.println(line2);
			int order = inOrder(line1, line2);
			if(order == - 1) {
				outOfOrder++;
				System.out.println(indices+"----------");
			} else if(order == 1) {
				inOrder++;
				indicesSum += indices;
				System.out.println(indices+"++++++++++");
			} else if(order == 0) {
				noDecision++;
			}
		}
		sc.close();
		System.out.println("In Order: " + inOrder + " Out of Order: " + outOfOrder + " No Decision: " + noDecision);
		System.out.println("Indices Sum: " + indicesSum);
	}
	
	//Returns 1 if the packages are in order, 
	//0 if there is no order detected, 
	//-1 if they are out of order
	public static int inOrder(String left, String right) {
		System.out.println(left);
		System.out.println(right);
		System.out.println("Compare " + left + " vs " + right);
		//null cases
		if((left == null || left.isEmpty()) && right != null && !right.isEmpty()) { //left ran out of items, so in order 
			//unpack the right side and check again
			System.out.println("Returning 1 here");
			new Exception().printStackTrace();
			return 1;
			
			//System.out.println("left side ran out of items");
			//return 1;
		} else if(left != null && !left.isEmpty() && (right == null || right.isEmpty())) {
			return -1;
		} else if((left == null || left.isEmpty()) && (right == null || right.isEmpty())) {
			System.out.println("Returned 0");
			return 0;
		}
		
		//now handle the empty cases
		if(left.isEmpty() && !right.isEmpty()) { //left ran out of items, so in order 
			//System.out.println("left side ran out of items 2");
			return 1;
		} else if(!left.isEmpty() && right.isEmpty()) {
			return -1;
		} else if(left.isEmpty() && right.isEmpty()) {
			System.out.println("Returned 0");
			return 0;
		}
		if(left.charAt(0) == '[' && right.charAt(0) == '[') {//list comparison
			String[] newLeft = unpack(left);
			String[] newRight = unpack(right);
			int order = inOrder(newLeft[0], newRight[0]);
			if(order == 0) {//lists are the same
				return inOrder(newLeft[1], newRight[1]); //compare the next parts
			}
			return order;
		} else if (left.charAt(0) != '[' && right.charAt(0) != '[') { 
			
		}else if(left.charAt(0) != '[') { //pack it up
			int comma = left.indexOf(',');
			if(comma == -1) {
				comma = left.length();
			}
			String newLeft = "[" + left.substring(0, comma) + "]" + left.substring(comma);
			System.out.println("Changed to " +newLeft);
			return inOrder(newLeft, right);
		} else if(right.charAt(0) != '[') { //pack it up
			int comma = right.indexOf(',');
			if(comma == -1) {
				comma = right.length();
			}
			String newRight = "[" + right.substring(0, comma) + "]" + right.substring(comma);
			System.out.println("Changed to " +newRight);
			return inOrder(left, newRight);
			
		}
		//now only numbers are left
		int leftComma = left.indexOf(',');
		int rightComma = right.indexOf(',');
		if(leftComma == -1) {
			leftComma = left.length();
		}
		if(rightComma == -1) {
			rightComma = right.length();
		}
		//System.out.println(left);
		int leftNumber = Integer.parseInt(left.substring(0, leftComma));
		int rightNumber = Integer.parseInt(right.substring(0, rightComma));
		if (leftNumber < rightNumber) {
			//System.out.println("Left side is smaller than right side");
			return 1;
		} else if (leftNumber > rightNumber) {
			return -1;
		} else {
			String newLeft = null;
			String newRight = null;
			if(leftComma + 1 <= left.length()) {
				newLeft = left.substring(leftComma + 1);				
			}
			if(rightComma + 1 <= right.length()) {
				newRight = right.substring(rightComma + 1);
			}
			return inOrder(newLeft, newRight);
		}
	}
	
	//unpack the most front package and return the content and what's outside of it
	public static String[] unpack(String pack) {
		if(pack.charAt(0) != '[') {
			return new String[]{pack, null};
		}
		int opened = 0;
		int closed = 0;
		String[] parts = new String[2];
		for(int i = 0; i < pack.length(); i++) {
			if(pack.charAt(i) == '[') {
				opened++;
			} else if(pack.charAt(i) == ']') {
				closed++;
			}
			if(opened != 0 && opened == closed) {
				parts[0] = pack.substring(1, i).trim();
				if(pack.length() > i + 2) {
					parts[1] = pack.substring(i + 2, pack.length()).trim();					
				}
				break;
			}
		}
		return parts;
	}
}
