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
		
		List<Cube> cubeList = new ArrayList<Cube>();
		while(sc.hasNextLine()) {
			String[] input = sc.nextLine().trim().split(",");
			Cube cube = new Cube(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2]));
			cubeList.add(cube);
		}
		sc.close();
		int count = 0;
		for(Cube c: cubeList) {
			if(!cubeList.contains(new Cube(c.x + 1, c.y, c.z))) {
				count++;
			}
			if(!cubeList.contains(new Cube(c.x - 1, c.y, c.z))) {
				count++;
			}
			if(!cubeList.contains(new Cube(c.x, c.y + 1, c.z))) {
				count++;
			}
			if(!cubeList.contains(new Cube(c.x, c.y - 1, c.z))) {
				count++;
			}
			if(!cubeList.contains(new Cube(c.x, c.y, c.z + 1))) {
				count++;
			}
			if(!cubeList.contains(new Cube(c.x, c.y, c.z - 1))) {
				count++;
			}
		}
		
		System.out.println(count);
	}

}
