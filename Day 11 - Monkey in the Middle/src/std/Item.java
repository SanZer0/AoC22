package std;

public class Item {
	private long worryLevel;

	public Item(int worryLevel) {
		this.worryLevel = worryLevel;
	}
	
	public long GetWorryLevel() {
		return worryLevel;
	}
	
	public void SetWorryLevel(long worryLevel) {
		this.worryLevel = worryLevel;
	}

}
