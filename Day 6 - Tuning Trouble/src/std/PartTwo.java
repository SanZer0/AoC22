/**
 * 
 */
package std;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author SanZer0
 *
 */
public class PartTwo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream stream = new FileInputStream("input.txt");
		int detectionLength = 14;
		int position = detectionLength;
		char[] chars = new char[detectionLength];
		//get the first four letters
		for(int i = 0; i < detectionLength; i++) {
			chars[i] = (char)stream.read();
		}
		
		System.out.println();
		whileloop:
		while(stream.available() > 0) {
			//check the current chars if all are unique
			outerloop:
			for(int i = 0; i < detectionLength; i++) {
				if(i == detectionLength - 1) { //no duplicates, so found a flag
					break whileloop;
				}
				//iterate through word
				for(int j = i+1; j < detectionLength; j++) {
					if(chars[i] == chars[j]) {//duplicate found, go to next char
						break outerloop;
					}					
				}
				
			}
			//read in the next char
			for(int i = 0; i < detectionLength - 1; i++) {
				chars[i] = chars[i + 1];
			}
			chars[detectionLength - 1] = (char)stream.read();
			for(int i = 0; i < detectionLength; i++) {
				System.out.print(chars[i]);
			}
			System.out.println();
			position++;
		}
		
		System.out.println("Found a marker at position " + position);
	}

}
