/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
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
		int searchPosition = 10;
		List<Sensor> sensors = new ArrayList<Sensor>();
		List<Beacon> beacons = new ArrayList<Beacon>();
		while(sc.hasNextLine()) {
			//Sensor at x=2332081, y=2640840: closest beacon is at x=2094728, y=2887414
			String[] line = sc.nextLine().trim().split(" ");
			//just hardcode this lul
			int x = Integer.parseInt(line[2].substring(2, line[2].length()-1));
			int y = Integer.parseInt(line[3].substring(2, line[3].length()-1));
			int beacx = Integer.parseInt(line[8].substring(2, line[8].length()-1));
			int beacy = Integer.parseInt(line[9].substring(2, line[9].length()));
			Beacon beacon = new Beacon(beacx, beacy);
			sensors.add(new Sensor(x, y, beacon));
			if(!beacons.contains(beacon)) {beacons.add(beacon);}
		}
		sc.close();
		//calculate the ranges in line 2000000
		List<Range> ranges = calculateRange(searchPosition, sensors);
		combineRanges(ranges);
		partOne(ranges, beacons, searchPosition);
		
		partTwo(sensors, beacons);
	}
	
	static void partTwo(List<Sensor> sensors, List<Beacon> beacons) {
		int max = 4000000;
		int missingNo = 0;
		int y = 0;
		for(int i = 0; i < max; i++) {
			List<Range> ranges = calculateRange(i, sensors);
			combineRanges(ranges);
			cutRanges(ranges, 0, max);
			if(partOne(ranges, beacons, i) == max) {
				System.out.println(i);
				for(Range r: ranges) {
					if(r.startX != 0) {
						missingNo = r.startX - 1;
						y = i;
					}
					System.out.println(missingNo);
					System.out.println(r);
				}
			}	
		}
		System.out.println(new BigInteger(4000000+"").multiply(new BigInteger(missingNo+"")).add(new BigInteger(y+"")));
		
	}
	
	static void cutRanges(List<Range> ranges, int min, int max) {
		for(Range r : ranges) {
			if(r.startX < min) {
				r.startX = min;
			}
			if(r.endX > max) {
				r.endX = max;
			}
			if(r.startX > max) {
				ranges.remove(r);
			}
			if(r.endX < min) {
				ranges.remove(r);
			}
		}
	}
	
	static List<Range> calculateRange(int searchPosition, List<Sensor> sensors) {
		List<Range> ranges = new ArrayList<>();
		for(Sensor sense : sensors) {
			int yDiff = Sensor.norm(sense.getY() - searchPosition);
			int startX = 0;
			int endX = 0;
			int blockedFields = 0;
			if(sense.getY() - sense.getManhattan() <= searchPosition && 
					sense.getY() + sense.getManhattan() >= searchPosition) {
				//blockCount = 1 + 2*(yDiff - man)
				int blockCount = 2*sense.getManhattan() + 1;//amount of blocks in the same row
				int blockCountSearch = blockCount - 2*yDiff; //amount of blocks in the desired row
				startX = sense.getX() - blockCountSearch/2;
				endX = sense.getX() + blockCountSearch/2;
				Range newRange = new Range(startX, endX); 
				ranges.add(newRange);
				//System.out.println(sense +" "+sense.getManhattan());
				//System.out.println(newRange);
			}	
		}
		return ranges;
	}
	
	static List<Range> combineRanges(List<Range> ranges) {
		for(int i = 0; i < ranges.size(); i++) {
			Range r = ranges.get(i);
			for(int j = i+1; j < ranges.size(); j++) {
				if(i == j) {continue;}
				if(r.combine(ranges.get(j))) {
					ranges.remove(j);
					i = i-1;
					break;
				} 
			}
		}
		return ranges;
	}
	
	static int partOne(List<Range> ranges, List<Beacon> beacons, int searchPosition) {
		int size = 0;
		for(Range r: ranges) {
			//System.out.println(r);
			size += r.getSize();
		}
		int beaconCount = 0;
		for(Beacon b: beacons) {
			//System.out.println(b);
			//System.out.println(b);
			for(Range r : ranges) {
				if(r.overlaps(new Range(b.x, b.x)) && b.y == searchPosition) {
					beaconCount++;
					break;
				}
			}
		}
		//System.out.println(beaconCount);
		//System.out.println(size - beaconCount);
		//ALERT: only for part two
		return size;
	}
	
}
