/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author SanZer0
 *
 */
public class Main {

	static boolean partTwo = false;
	
	static HashMap<String, String> monkeyMathMap;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("input.txt");
		Scanner sc = new Scanner(file);
		monkeyMathMap = new HashMap<>();
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] monkey = line.split(" ", 2);
			if(partTwo && monkey[0].equals("root:")) {
				String[] change = monkey[1].split(" ");
				change[1] = "==";
				monkey[1] = change[0] + " " + change[1] + " " + change[2];
				System.out.println(monkey[1]);
				
			}
			if(partTwo && monkey[0].equals("humn:")) {
				monkey[1] = "findOut";
			}
			monkeyMathMap.put(monkey[0].replace(":", ""), monkey[1].trim());
		}
		sc.close();
		//System.out.println(partOne());			
		System.out.println(partTwo());
		
	}
	
	public static String partTwo() {
		evaluate(monkeyMathMap, true);
		for(Map.Entry<String, String> e : monkeyMathMap.entrySet()) {
			if(e.getKey().equals("root")) {
				System.out.println("yo");
				monkeyMathMap.put("root", e.getValue().replace("+", "=="));
			}
		}
		//now evaluate root
		//monkeyMathMap.put("root", );
		boolean noResult = true;
		long n = 3342154812530l;
		//long n = 300;
		while(noResult) {
			HashMap<String, String> mapCopy = new HashMap<String, String>(monkeyMathMap);
			mapCopy.put("humn", Long.toString(n));
			evaluate(mapCopy, false);
			if(mapCopy.get("root").equals("true")) {
				System.out.println(n);
				noResult = false;
			}
			mapCopy = new HashMap<String, String>(monkeyMathMap);
			mapCopy.put("humn", Long.toString(-n));
			evaluate(mapCopy, false);
			if(mapCopy.get("root").equals("true")) {
				System.out.println(-n);
				noResult = false;
			}
			System.out.println(n);
			n+= 1l;
		}
		
		//repl now is the whole equation, create a tree out of it
		return Long.toString(n);
	}
	
	public static void evaluate(Map<String, String> map, boolean skip) {
		Queue<Map.Entry<String, String>> queue = new LinkedList<>();
		for(Map.Entry<String, String> entry : map.entrySet()) {
			if(!entry.getValue().contains(" ")) {
				queue.add(entry);
			}
		}
		while(!queue.isEmpty()) {
			Map.Entry<String, String> entry = queue.remove();
			if(entry.getKey().equals("root")) {
				break;
			}
			if(entry.getKey().equals("humn") && skip) {
				continue;
			}
			
			for(Map.Entry<String, String> checkEntry : map.entrySet()) {
				if(checkEntry.getValue().contains(entry.getKey())) {
					String formula = checkEntry.getValue().replace(entry.getKey(), entry.getValue());
					String[] formulaSplit = formula.split(" ");
					if(isNumeric(formulaSplit[0]) && isNumeric(formulaSplit[2])) {
						if(checkEntry.getKey().equals("root")) {
							map.put("root", (formulaSplit[0].equals(formulaSplit[2])? "true" : "false"));
							System.out.println("Checking " + formulaSplit[0] + " == " + formulaSplit[2] + " Diff: " + Math.abs(Long.parseLong(formulaSplit[0])- Long.parseLong(formulaSplit[2])));
							break;
						}
						long result = calculate(formulaSplit);
						formula = Long.toString(result);
						checkEntry.setValue(formula);
						queue.add(checkEntry);
					}
					map.put(checkEntry.getKey(), formula);
				}
			}
		}
	}
	
	public static String partOne() {
		Queue<Map.Entry<String, String>> queue = new LinkedList<>();
		for(Map.Entry<String, String> entry : monkeyMathMap.entrySet()) {
			if(!entry.getValue().contains(" ")) {
				queue.add(entry);
			}
		}
		while(!queue.isEmpty()) {
			Map.Entry<String, String> entry = queue.remove();
			if(entry.getKey().equals("root")) {
				return entry.getValue();
			}
			
			for(Map.Entry<String, String> checkEntry : monkeyMathMap.entrySet()) {
				if(checkEntry.getValue().contains(entry.getKey())) {
					String formula = checkEntry.getValue().replace(entry.getKey(), entry.getValue());
					String[] formulaSplit = formula.split(" ");
					if(isNumeric(formulaSplit[0]) && isNumeric(formulaSplit[2])) {
						long result = calculate(formulaSplit);
						formula = Long.toString(result);
						checkEntry.setValue(formula);
						queue.add(checkEntry);
					}
					monkeyMathMap.put(checkEntry.getKey(), formula);
				}
			}
		}
		return null;
	}
	public static long calculate(String[] formulaSplit) {
		long result = 0;
		switch(formulaSplit[1]) {
			case "+": result = Long.parseLong(formulaSplit[0]) + Long.parseLong(formulaSplit[2]);
				break;
			case "-": result = Long.parseLong(formulaSplit[0]) - Long.parseLong(formulaSplit[2]); 
				break;
			case "/": result = Long.parseLong(formulaSplit[0]) / Long.parseLong(formulaSplit[2]);
				break;
			case "*": result = Long.parseLong(formulaSplit[0]) * Long.parseLong(formulaSplit[2]);
				break;
		}
		return result;
	}
	
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}	
}
