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
public class PartOne {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream stream = new FileInputStream("input.txt");
		while(stream.available() > 0) {
			System.out.print(stream.read());
		}
	}

}
