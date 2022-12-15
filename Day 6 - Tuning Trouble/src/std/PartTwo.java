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
		int position = 4;
		char[] chars = new char[4];
		//get the first four letters
		for(int i = 0; i < 4; i++) {
			chars[i] = (char)stream.read();
		}
		
		System.out.println();
		whileloop:
		while(stream.available() > 0) {
			//check the current chars if all are unique
			outerloop:
			for(int i = 0; i < 4; i++) {
				if(i == 3) { //no duplicates, so found a flag
					break whileloop;
				}
				//iterate through word
				for(int j = i+1; j < 4; j++) {
					if(chars[i] == chars[j]) {//duplicate found, go to next char
						break outerloop;
					}					
				}
				
			}
			//read in the next char
			for(int i = 0; i <= 2; i++) {
				chars[i] = chars[i + 1];
			}
			chars[3] = (char)stream.read();
			for(int i = 0; i < 4; i++) {
				System.out.print(chars[i]);
			}
			System.out.println();
			position++;
		}
		
		System.out.println("Found a marker at position " + position);
	}

}
