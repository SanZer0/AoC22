/**
 * 
 */
package std;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
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
		sc.close();
		//printTree(tree);
		//System.out.println();
		propagateSizes(tree);
		//System.out.println(sumNodesUnderSize(100000, tree));
		
		//Part Two
		int diskSpace = 70000000;
		int neededSpace = 30000000;
		int totalUsedSpace = tree.GetRoot().GetData().GetSize();
		int spaceToFree = neededSpace - (diskSpace - totalUsedSpace);
		Queue<Node<Directory>> dirsOverNeeded = findNodesOverSize(spaceToFree, tree);
		Node<Directory> best = dirsOverNeeded.remove();
		for(Node<Directory> next: dirsOverNeeded) {
			if(best.GetData().GetSize() > next.GetData().GetSize()) {
				best = next;
			}
		}
		System.out.println(best.GetData().GetSize());
	}
	//add the sizes of child nodes(and further) to the parent node
	public static void propagateSizes(Tree<Directory> t) {
		propagateSizes(t.GetRoot());
	}
	public static void propagateSizes(Node<Directory> t) {
		Directory dir = t.GetData();
		int tempSize = 0;
		for(Node<Directory> n: t.GetChilds()) {
			propagateSizes(n);
			tempSize += n.GetData().GetSize();
		}
		dir.SetSize(dir.GetSize() + tempSize);
		t.SetData(dir); 
	}
	
	//count the amount of nodes with a given maximum size
	public static int sumNodesUnderSize(int size, Tree<Directory> tree) {
	//keine Rekursion
		int totalSize = 0;
		Queue<Node<Directory>> list = new LinkedList<Node<Directory>>();
		Node<Directory> currentNode = tree.GetRoot();
		list.add(currentNode);
		while(!list.isEmpty()) {
			currentNode = list.remove();
			currentNode.GetChilds().forEach(child -> list.add(child));
			if(currentNode.GetData().GetSize() <= size) {
				//System.out.println(currentNode.GetData().GetName() + " has size of " + currentNode.GetData().GetSize());
				totalSize += currentNode.GetData().GetSize();
			}
		}
		return totalSize;
	}
	
	public static Queue<Node<Directory>> findNodesOverSize(int size, Tree<Directory> tree) {
		Queue<Node<Directory>> list = new LinkedList<Node<Directory>>();
		Queue<Node<Directory>> approved = new LinkedList<Node<Directory>>();
		Node<Directory> currentNode = tree.GetRoot();
		list.add(currentNode);
		while(!list.isEmpty()) {
			currentNode = list.remove();
			currentNode.GetChilds().forEach(child -> list.add(child));
			if(currentNode.GetData().GetSize() >= size) {
				//System.out.println(currentNode.GetData().GetName() + " has size of " + currentNode.GetData().GetSize());
				approved.add(currentNode);
			}
		}
		return approved;
	}
	//Print the tree
	public static void printTree(Tree<Directory> t) {
		printNode(t.GetRoot());
	}
	
	public static void printNode(Node<Directory> n) {
		System.out.print(n.GetData().GetName());
		if(!n.GetChilds().isEmpty()) {
			System.out.print("(");
			n.GetChilds().forEach(child -> {
				printNode(child);
				System.out.print(",");
			});
			System.out.print(")");			
		}
		
	}
}
