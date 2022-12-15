/**
 * 
 */
package std;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SanZer0
 *
 */
public class Tree<T> {
	private Node<T> root;
	
	public Tree(T rootData) {
		root = new Node<T>(rootData);
		root.data = rootData;
		root.children = new ArrayList<Node<T>>();
	}
	
	public Node<T> GetRoot() {
		return root;
	}
	
	
	public static class Node<T> {
		private T data;
		private Node<T> parent;
		private List<Node<T>> children;
		
		public Node(T data) {
			children = new ArrayList<Node<T>>();
			this.data = data;
		}
		
		public void SetChild(Node<T> child) {
			children.add(child);
		}
		
		public List<Node<T>> GetChilds() {
			return children;
		}
		
		public void SetParent(Node<T> parent) {
			this.parent = parent;
		}
		
		public Node<T> GetParent() {
			return parent;
		}
		
		public T GetData() {
			return data;
		}
		public void SetData(T data) {
			this.data = data;
		}
		
	}
}
