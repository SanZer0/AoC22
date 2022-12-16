/**
 * 
 */
package std;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author SanZer0
 *
 */
public class Monkey {
	private Queue<Item> itemQueue;
	private Item currentItem;
	private String[] operation;
	private int divisible;
	private List<Monkey> monkeys;
	private int trueThrow;
	private int falseThrow;
	private int inspected;
	
	public Monkey(List<Monkey> monkeys) {
		itemQueue = new LinkedList<Item>();
		inspected = 0;
		this.monkeys = monkeys;
	}
	/**
	 * 
	 */
	public Monkey(String operation, int divisible, List<Monkey> monkeys, int trueThrow, int falseThrow) {
		itemQueue = new LinkedList<Item>();
		this.operation = operation.trim().split(" ");
		this.divisible = divisible;
		this.monkeys = monkeys;
		this.trueThrow = trueThrow;
		this.falseThrow = falseThrow;
		inspected = 0;
	}
	
	public void GiveItem(Item item) {
		itemQueue.add(item);
	}
	//All the stuff that happens when throwing an item
	public void ThrowItem() {
		currentItem = itemQueue.remove();
		long worryLevel = currentItem.GetWorryLevel();
		long factor = 0;
		if(operation[2].equals("old")) {
			factor = worryLevel;
		} else {
			factor = Integer.parseInt(operation[2]);
		}
		switch(operation[1]) {
			case "*": 	worryLevel *= factor; 
					break;
			case "+":	worryLevel += factor;
					break;
			default: break;
			
		}
		worryLevel = worryLevel / 3;
		currentItem.SetWorryLevel(worryLevel);
		if(worryLevel % divisible == 0) {
			monkeys.get(trueThrow).GiveItem(currentItem);
		} else {
			monkeys.get(falseThrow).GiveItem(currentItem);
		}
		inspected++;
	}
	
	public boolean HasItems() {
		return !itemQueue.isEmpty();
	}
	
	//Setters
	public void SetTrueThrow(int monkey) {
		trueThrow = monkey;
	}
	public void SetFalseThrow(int monkey) {
		falseThrow = monkey;
	}
	
	public void SetOperation(String[] operation) {
		this.operation = operation;
	}
	
	public void SetDivisor(int divisor) {
		divisible = divisor;
	}
	
	public int GetInspected() {
		return inspected;
	}
	
	public Queue<Item> GetItems() {
		return new LinkedList<Item>(itemQueue);
	}
}
