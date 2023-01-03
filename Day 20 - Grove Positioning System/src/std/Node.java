/**
 * 
 */
package std;

/**
 * @author SanZer0
 *
 */
public class Node {

	long value;
	int position;
	Node previous;
	Node next;
	
	/**
	 * 
	 */
	public Node(long value, int position) {
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
	
	public long getValue() {
		return value;
	}
	
	public int getPosition() {
		return position;
	}
}
