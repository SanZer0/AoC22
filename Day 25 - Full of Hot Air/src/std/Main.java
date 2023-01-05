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
		
		long sum = 0;
		while(sc.hasNextLine()) {
			String number = sc.nextLine().trim();
			sum += snafuToLong(number);
		}
		sc.close();
		System.out.println("Integer: " + sum);
		System.out.println("SNAFU: " + longToSnafu(sum));

	}
	
	//always go to the nearest number to 0 to get the correct factor to calculate the snafu
	public static String longToSnafu(long sum) {
		long max = 0;
		int counter = -1;
		while(max < sum) {
			counter++;
			max += 2* Math.pow(5, counter);
		}
		
		String snafu = "";
		while(counter >= 0) {
			long smallest = Long.MAX_VALUE;
			int factor = 0;
			for(int i = -2; i <= 2; i++) {
				long before = smallest;
				smallest = (long) Math.min(smallest, Math.abs(sum - i * Math.pow(5, counter)));
				if(before != smallest) {
					factor = i;
				}
			}
			switch(factor) {
				case -2: snafu = snafu + "=";
					break;
				case -1: snafu = snafu + "-";
					break;
				default: snafu = snafu + "" + factor;
					break;
			}
			sum -= factor * Math.pow(5, counter);
			counter--;
		}
		return snafu;
	}

	public static long snafuToLong(String snafu) {
		long sum = 0;
		for(int i = snafu.length() - 1; i >= 0; i--) {
			char symbol = snafu.charAt(i);
			int currNumber;
			switch(symbol) {
				case '-': currNumber = -1;
					break;
				case '=': currNumber = -2;
					break;
				default: currNumber = Character.getNumericValue(symbol);
			}
			sum += Math.pow(5, (snafu.length() - 1) - i) * currNumber;
		}
		return sum;
	}
}
