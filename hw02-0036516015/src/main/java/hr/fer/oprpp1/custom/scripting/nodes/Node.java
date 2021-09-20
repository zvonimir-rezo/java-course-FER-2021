package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Abstract class representing a node in a tree.
 * All other node classes extend this class.
 * @author Zvonimir Petar Rezo
 *
 */
public abstract class Node {
	
	protected ArrayIndexedCollection children;
	private boolean firstCallChildNode;
	
	/**
	 * Default constructor. Just sets everything to null.
	 */
	public Node() {
		children = null;
		firstCallChildNode = true;
	}
	
	/**
	 * Adds child node to the current node. Current node becomes parent of the child node.
	 * @param child - node that needs a parent
	 */
	public void addChildNode(Node child) {
		if (firstCallChildNode) {
			firstCallChildNode = false;
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Getter for size of the children array.
	 * @return Number of children current node possesses.
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Getter for a child at given index in array of children.
	 * @param index - index of a child user wants to get
	 * @return Child node at a given index.
	 */
	public Node getChild(int index) {
		return (Node) children.get(index);
	}
	
}
