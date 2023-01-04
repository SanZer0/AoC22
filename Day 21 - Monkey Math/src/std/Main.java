/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
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
			monkeyMathMap.put(monkey[0].replace(":", ""), monkey[1].trim());
		}
		sc.close();
		
		Queue<Map.Entry<String, String>> queue = new LinkedList<>();
		for(Map.Entry<String, String> entry : monkeyMathMap.entrySet()) {
			if(!entry.getValue().contains(" ")) {
				queue.add(entry);
			}
		}
		while(!queue.isEmpty()) {
			Map.Entry<String, String> entry = queue.remove();
			if(entry.getKey().equals("root")) {
				System.out.println(entry.getValue());
				break;
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
