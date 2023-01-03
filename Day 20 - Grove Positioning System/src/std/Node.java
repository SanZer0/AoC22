/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Node {

	int value;
	int position;
	Node previous;
	Node next;
	
	/**
	 * 
	 */
	public Node(int value, int position) {
		this.value = value;
		this.position = position;
	}
	
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Node getPrevious() {
		return previous;
	}
	public Node getNext() {
		return next;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getPosition() {
		return position;
	}
}
