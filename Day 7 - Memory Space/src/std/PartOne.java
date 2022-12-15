/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import std.Tree.Node;

/**
 * @author Jonas
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
		Tree<Directory> tree = new Tree<Directory>(new Directory("/"));
		Node<Directory> currentNode = tree.GetRoot();
		while(sc.hasNextLine()) {
			String[] line = sc.nextLine().trim().split(" ");
			
			if(line[0].equals("$")) { //command
				if(line[1].equals("ls")) {} //show files, no action needed
				else if(line[1].equals("cd")) { //move in the fs
					if(line[2].equals("..")) { //move out
						currentNode = currentNode.GetParent();
					}else if(line[2].equals("/")) {//zoom all out
						currentNode = tree.GetRoot();
					}else{ //move in
						Node<Directory> newNode = new Node<Directory>(new Directory(line[2]));
						currentNode.SetChild(newNode);
						newNode.SetParent(currentNode);
						currentNode = newNode;
					}
				}
			} else if(line[0].equals("dir")){//directory
				//just ignore it, don't need it
			} else {//file
				Directory currentDir = currentNode.GetData();
				currentDir.SetSize(currentDir.GetSize() + Integer.parseInt(line[0]));
			}
		}
		//PrintTree(tree);
		PropagateSizes(tree);
		System.out.println(tree.GetRoot().GetData().GetSize());
		sc.close();
	}
	public static void PropagateSizes(Tree<Directory> t) {
		PropagateSizes(t.GetRoot());
	}
	
	public static void PropagateSizes(Node<Directory> t) {
		Directory dir = t.GetData();
		int tempSize = 0;
		for(Node<Directory> n: t.GetChilds()) {
			PropagateSizes(n);
			tempSize += n.GetData().GetSize();
		}
		dir.SetSize(dir.GetSize() + tempSize);
		t.SetData(dir); 
	}
	
	//Print the tree
	public static void PrintTree(Tree<Directory> t) {
		PrintNode(t.GetRoot());
	}
	
	public static void PrintNode(Node<Directory> n) {
		System.out.print(n.GetData().GetName());
		if(!n.GetChilds().isEmpty()) {
			System.out.print("(");
			n.GetChilds().forEach(child -> {
				PrintNode(child);
				System.out.print(",");
			});
			System.out.print(")");			
		}
		
	}
}
