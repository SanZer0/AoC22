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
public class PartOne {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		int[] sandStart = {500, 0};
		//create dungeon
		List<List<Character>> map = new ArrayList<List<Character>>();
		List<Coords> coordinates = new ArrayList<Coords>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine().trim();
			String[] coords = line.split(" -> ");
			//take from a -> b both a and b 
			for(int i = 0; i < coords.length - 1; i++) {
				String[] a = coords[i].split(",");
				String[] b = coords[i+1].split(",");
				int coord1_x = Integer.parseInt(a[0]);
				int coord1_y = Integer.parseInt(a[1]);
				int coord2_x = Integer.parseInt(b[0]);
				int coord2_y = Integer.parseInt(b[1]);
				int factor_x = 1;
				int factor_y = 1;
				//some cases to cycle through everything
				if(coord1_x == coord2_x) {
					if(coord1_y < coord2_y) {
						for(int y = coord1_y; y <= coord2_y; y++) {
							coordinates.add(new Coords(coord1_x, y));						
						}
					} else {
						for(int y = coord1_y; y >= coord2_y; y--) {
							coordinates.add(new Coords(coord1_x, y));
						}
					}
				}
				if(coord1_y == coord2_y) {
					if(coord1_x < coord2_x) {
						for(int x = coord1_x; x <= coord2_x; x++) {
							coordinates.add(new Coords(x, coord1_y));
						}
					} else {
						for(int x = coord1_x; x >= coord2_x; x--) {
							coordinates.add(new Coords(x, coord1_y));
						}
					}
				}
				if(coord1_y > coord2_y) {
					factor_y = -1;
				}
				
			}	
		}
		sc.close();
		//WATCH OUT: There's errors in naming from x and y due to confusion. this is dirtily fixed
		//but beware...
		//get the size of the array
		int[] x_sizes = {Integer.MAX_VALUE, 500};
		int[] y_sizes = {0, 0};
		for(Coords c : coordinates) {
			if(c.x < x_sizes[0]) { //min x
				x_sizes[0] = c.x;
			}
			if(c.x > x_sizes[1]) { //max x
				x_sizes[1] = c.x + 1;
			}
			if(c.y < y_sizes[0]) { //min y
				y_sizes[0] = c.y;
			}
			if(c.y > y_sizes[1]) { //max y
				y_sizes[1] = c.y + 1 + 1;
			}
		}
		x_sizes[0] = x_sizes[0] - 300;
		x_sizes[1] = x_sizes[1] + 300;
		//fill up the map
		for(int i = x_sizes[0]; i <= x_sizes[1]; i++) {//x values from a - 500 - b
			ArrayList<Character> list = new ArrayList<Character>();
			map.add(list);
				for(int j = y_sizes[0]; j <= y_sizes[1]; j++) { //y values from 0 to idk
				if(i == 500 && j == 0) {
					list.add('+');
				} else if (j == y_sizes[1]) {
					list.add('#');
				} else {
					list.add('.');			
				}
			}
		}
		
		//put the coordinates into the map
		for(Coords c: coordinates) {
			//System.out.println("x = " + c.x + "y = " + c.y);
			map.get(c.x - x_sizes[0]).set(c.y, '#');
		}
		/*
		System.out.println("First map is size " + map.size());//x
		System.out.println("Max X: " + x_sizes[1] + " Min X: " + x_sizes[0]);//x
		System.out.println("Second map is size " + map.get(0).size());//y
		System.out.println("Max Y: " + y_sizes[1] + " Min Y: " + y_sizes[0]);//y
		*/
		int sandDropped = 0;
		boolean finished = false;
		while(!finished) {
			finished = !dropSand(sandStart, map, x_sizes, y_sizes);
			if(!finished) {
				sandDropped++;			
			}
		}
		printMap(x_sizes, y_sizes, map);
		System.out.println("Dropped " + sandDropped + " times");
		
	}
	//drop a corn of sand
	public static boolean dropSand(int[] sandStart, List<List<Character>> map, int[] x_sizes, int[] y_sizes) {
		int x = sandStart[0];
		int y = sandStart[1];
		if(map.get(x - x_sizes[0]).get(y) != '+') {
			System.out.println("Source is blocked");
			return false;
		}
		//System.out.println("X = " + x + " " + "Y = " + y);
		//System.out.println(x_sizes[0] + " " + y_sizes[0] + " " + y_sizes[1]);
		while(x > x_sizes[0] && x < x_sizes[1] && y + 1 > y_sizes[0]) {
			/*if(map.get(x - x_sizes[0]).get(y + 1) == '#') { //boden drunter, drop einfach??? ne
				map.get(x - x_sizes[0]).set(y, 'o');
				return true;
			}*/
			if(y + 1 >= map.get(0).size()) {//Dropped out of frame down
				return false;
			}
			if(map.get(x - x_sizes[0]).get(y + 1) == '.') { //plain air, just drop
				x = x;
				y = y + 1;
			} else if(map.get(x - x_sizes[0]).get(y + 1) == 'o' || 
					map.get(x - x_sizes[0]).get(y + 1) == '#') {//sand or ground, so go left or right
				if(x+1 < x_sizes[1] && map.get(x - x_sizes[0] - 1).get(y + 1) == '.') { //try left
					x = x - 1;
					y = y + 1;
				} else if(x+1 < x_sizes[1] && map.get(x - x_sizes[0] + 1).get(y + 1) == '.') { //try right
					x = x + 1;
					y = y + 1;
				} else { //bleib liegen
					map.get(x - x_sizes[0]).set(y, 'o');
					return true;
				}
			}
		}
		return false;
	}
	//visualize the map
	public static void printMap(int[] x_sizes, int[] y_sizes, List<List<Character>> map) {
		System.out.println("x: "+x_sizes[0]+ "y: " + y_sizes[0]);
		//print the list
		for(int j = y_sizes[0]; j <= y_sizes[1]; j++) {
			System.out.print(j);
			for(int i = x_sizes[0]; i <= x_sizes[1]; i++) {
				System.out.print(map.get(i - x_sizes[0]).get(j));
			}
			System.out.println();
		}
	}
}
//Coords class needed
class Coords {
	public Coords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Coords(int x, int y, int steps, Character height) {
		this.x = x;
		this.y = y;
		this.steps = steps;
		this.height = height;
	}
	public int x;
	public int y;
	public int steps;
	Character height;
}
