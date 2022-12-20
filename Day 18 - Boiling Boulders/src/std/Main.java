/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {
	public static List<Cube> cubeList;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		
		cubeList = new ArrayList<Cube>();
		while(sc.hasNextLine()) {
			String[] input = sc.nextLine().trim().split(",");
			Cube cube = new Cube(Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2]));
			cubeList.add(cube);
		}
		sc.close();
		PartOne(cubeList);
		
		PartTwo(cubeList);
		
	}
	/*
	private static void PartTwo(List<Cube> cubeList) {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minZ = Integer.MAX_VALUE;
		int maxZ = Integer.MIN_VALUE;
		//first get the bounding volumes
		for(Cube c: cubeList) {
			if(c.x < minX) {
				minX = c.x;
			} else if (c.x > maxX) {
				maxX = c.x;
			}
			if(c.y < minY) {
				minY = c.y;
			} else if (c.y > maxY) {
				maxY = c.y;
			}
			if(c.z < minZ) {
				minZ = c.z;
			} else if (c.z > maxZ) {
				maxZ = c.z;
			}
		}
		//create a 3d grid with an extra border
		Cube[][][] cubes = new Cube[maxX - minX + 3][maxY - minY + 3][maxZ-minZ + 3];
		for(int i = 0; i <= maxX - minX + 2; i++) {
			for(int j = 0; j <= maxY - minY + 2; j++) {
				for(int k = 0; k <= maxZ - minZ + 2; k++) {
					//System.out.println("X:" + i + " Y:" + j + " Z:" + k);
					Cube newCube = new Cube(i + minX, j + minY, k + minZ);
					cubes[i][j][k] = newCube;
				}
			}
		}
		//Cube compCube = new Cube(i + minX - 1, j + minY - 1, k + minZ - 1);
		for(Cube c:cubeList) {
			cubes[c.x-minX+1][c.y-minY+1][c.z-minZ+1].isLava = true;
			System.out.print("Found Lava at ");
			System.out.print("X:" + (c.x) + " Y:" + (c.y) + " Z:" + (c.z));
			System.out.print(" placed at ");
			System.out.println("X:" + (c.x-minX+1) + " Y:" + (c.y-minY+1) + " Z:" + (c.z-minZ+1));
		}
		
		//now do the bfs
		Queue<Cube> bfsQueue = new LinkedList<Cube>();
		bfsQueue.add(cubes[0][0][0]);
		cubes[0][0][0].touchesAir = true;
		Cube next = null;
		while(!bfsQueue.isEmpty()) {
			next = bfsQueue.remove();
			System.out.println("Cube " + next.x + " " +next.y+ " " + next.z);
			//check all directions
			int convX = next.x - minX;
			int convY = next.y - minY;
			int convZ = next.z - minZ;
			if(convX - 1 >= 0 && !cubes[convX - 1][convY][convZ].isLava && !cubes[convX - 1][convY][convZ].touchesAir) {
				bfsQueue.add(cubes[convX - 1][convY][convZ]);
				cubes[convX - 1][convY][convZ].touchesAir = true;
			}
			if(convX + 1 < cubes.length && !cubes[convX + 1][convY][convZ].isLava && !cubes[convX + 1][convY][convZ].touchesAir) {
				bfsQueue.add(cubes[convX + 1][convY][convZ]);
				cubes[convX + 1][convY][convZ].touchesAir = true;
			}
			if(convY - 1 >= 0 && !cubes[convX][convY - 1][convZ].isLava && !cubes[convX][convY - 1][convZ].touchesAir) {
				bfsQueue.add(cubes[convX][convY - 1][convZ]);
				cubes[convX][convY - 1][convZ].touchesAir = true;
			}
			if(convY + 1 < cubes[0].length && !cubes[convX][convY + 1][convZ].isLava && !cubes[convX][convY + 1][convZ].touchesAir) {
				bfsQueue.add(cubes[convX][convY + 1][convZ]);
				cubes[convX][convY + 1][convZ].touchesAir = true;
			}
			if(convZ - 1 >= 0 && !cubes[convX][convY][convZ - 1].isLava && !cubes[convX][convY][convZ - 1].touchesAir) {
				bfsQueue.add(cubes[convX][convY][convZ - 1]);
				cubes[convX][convY][convZ - 1].touchesAir = true;
			}
			if(convZ + 1 < cubes[0][0].length && !cubes[convX][convY][convZ + 1].isLava && !cubes[convX][convY][convZ + 1].touchesAir) {
				bfsQueue.add(cubes[convX][convY][convZ + 1]);
				cubes[convX][convY][convZ + 1].touchesAir = true;
			}
		}
		System.out.println(minX + "-" + maxX + "," + minY + "-" + maxY + "," + minZ + "-" + maxZ);
		
		int count = 0;
		for(Cube c: cubeList) {
			int countBefore = count;
			int convX = c.x - minX;
			int convY = c.y - minY;
			int convZ = c.z - minZ;
			if(convX - 1 >= 0 && !cubes[convX - 1][convY][convZ].isLava && cubes[convX - 1][convY][convZ].touchesAir) {
				count++;
			}
			if(convX + 1 < cubes.length && !cubes[convX + 1][convY][convZ].isLava && cubes[convX + 1][convY][convZ].touchesAir) {
				count++;
			}
			if(convY - 1 >= 0 && !cubes[convX][convY - 1][convZ].isLava && cubes[convX][convY - 1][convZ].touchesAir) {
				count++;
			}
			if(convY + 1 < cubes[0].length && !cubes[convX][convY + 1][convZ].isLava && cubes[convX][convY + 1][convZ].touchesAir) {
				count++;
			}
			if(convZ - 1 >= 0 && !cubes[convX][convY][convZ - 1].isLava && cubes[convX][convY][convZ - 1].touchesAir) {
				count++;
			}
			if(convZ + 1 < cubes[0][0].length && !cubes[convX][convY][convZ + 1].isLava && cubes[convX][convY][convZ + 1].touchesAir) {
				count++;
			}
			System.out.println("Added " + (count-countBefore) + " sides");
		}
		System.out.println(count);
	}
	*/
	static void PartOne(List<Cube> cubeList) {
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
	//just bfs outwards now, will take some time
	static void PartTwo(List<Cube> cubeList) {
		initializeAir(cubeList);
		int count = 0;
		Cube cubeToCheck;
		for(Cube c: cubeList) {//check a lavacube
			cubeToCheck = new Cube(c.x + 1, c.y, c.z);
			if(!cubeList.contains(cubeToCheck)) {//if it isn't a lavacube
				if(touchesAir(cubeToCheck)) {//and it touches air somehow
					count++;					//count up
				}
			}
			cubeToCheck = new Cube(c.x - 1, c.y, c.z);
			if(!cubeList.contains(cubeToCheck)) {
				if(touchesAir(cubeToCheck)) {
					count++;					
				}
			}
			cubeToCheck = new Cube(c.x, c.y+1, c.z);
			if(!cubeList.contains(cubeToCheck)) {
				if(touchesAir(cubeToCheck)) {
					count++;					
				}
			}
			cubeToCheck = new Cube(c.x, c.y-1, c.z);
			if(!cubeList.contains(cubeToCheck)) {
				if(touchesAir(cubeToCheck)) {
					count++;					
				}
			}
			cubeToCheck = new Cube(c.x, c.y, c.z+1);
			if(!cubeList.contains(cubeToCheck)) {
				if(touchesAir(cubeToCheck)) {
					count++;					
				}
			}
			cubeToCheck = new Cube(c.x, c.y, c.z-1);
			if(!cubeList.contains(cubeToCheck)) {
				if(touchesAir(cubeToCheck)) {
					count++;					
				}
			}
			System.out.println(count);
		}
		System.out.println(count);
	}
	
	
	public static List<Cube> airBlocks;
	static void initializeAir(List<Cube> lavaCubes) {
		airBlocks = new ArrayList<Cube>();
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minZ = Integer.MAX_VALUE;
		int maxZ = Integer.MIN_VALUE;
		for(Cube c : lavaCubes) {
			minX = Math.min(c.x, minX);
			minY = Math.min(c.y, minY);
			minZ = Math.min(c.z, minZ);
			maxX = Math.max(c.x, maxX);
			maxY = Math.max(c.y, maxY);
			maxZ = Math.max(c.z, maxZ);
		}
		for(int i = minX - 1; i <= maxX + 1; i++) {
			for(int j = minY - 1; j <= maxY + 1; j++) {
				airBlocks.add(new Cube(i, j, minZ-1));
				airBlocks.add(new Cube(i, j, maxZ+1));
			}
		}
		for(int i = minX - 1; i <= maxX + 1; i++) {
			for(int j = minZ - 1; j <= maxZ + 1; j++) {
				airBlocks.add(new Cube(i, minY-1, j));
				airBlocks.add(new Cube(i, maxY+1, j));
			}
		}
		for(int i = minY - 1; i <= maxY + 1; i++) {
			for(int j = minZ - 1; j <= maxZ + 1; j++) {
				airBlocks.add(new Cube(minX-1, i, j));
				airBlocks.add(new Cube(maxX+1, i, j));
			}
		}
		//air is initialized
	}
	static boolean touchesAir(Cube c) {
		if(airBlocks.contains(c)) {//is air
			System.out.println("Block at " + c.x + " " + c.y + " " + c.z + " touches Air");
			return true;
		} else if(cubeList.contains(c)) {//isLava
			return false;
		}
		Queue <Cube> cubeQueue = new LinkedList<Cube>();
		cubeQueue.add(c);
		Cube check;
		ArrayList<Cube> visited = new ArrayList<Cube>();
		while(!cubeQueue.isEmpty()) {
			check = cubeQueue.remove();
			if(visited.contains(check) || cubeList.contains(check)) {
				continue;
			}
			visited.add(check);
			if(airBlocks.contains(check)) {//is air
				airBlocks.add(c);
				return true;
			}
			cubeQueue.add(new Cube(check.x + 1, check.y, check.z));
			cubeQueue.add(new Cube(check.x - 1, check.y, check.z));
			cubeQueue.add(new Cube(check.x, check.y + 1, check.z));
			cubeQueue.add(new Cube(check.x, check.y - 1, check.z));
			cubeQueue.add(new Cube(check.x, check.y, check.z + 1));
			cubeQueue.add(new Cube(check.x, check.y, check.z - 1));
		}
		return false;
	}
	
}
