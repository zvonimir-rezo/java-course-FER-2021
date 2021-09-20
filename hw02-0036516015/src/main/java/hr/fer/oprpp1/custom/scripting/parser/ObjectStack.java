package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**Implementation of a stack of objects
 * 
 * @author Zvonimir Petar Rezo
 *
 */

public class ObjectStack {
		
	public ArrayIndexedCollection collection;
	
	/**
	 * Constructor, takes ArrayIndexedCollection as a
	 * object storage
	 * @param size - size of the stack
	 */
	public ObjectStack(int size) {
		this.collection = new ArrayIndexedCollection(size);
	}
	
	/**
	 * Checks if the stack is empty.
	 * @return True if the stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return this.collection.isEmpty();
	}
	
	/**
	 * Gets the size of the stack.
	 * @return Size of the stack.
	 */
	public int size() {
		return this.collection.size();
	}
	
	/**
	 * Checks if the stack is full.
	 * @return True if the stack is full, false otherwise.
	 */
	public boolean isFull() {
		return this.collection.size() == this.collection.getElements().length;
	}
	
	/**
	 * Pushes the given object to the top of the stack.
	 * @param value - Object value that is being put on top of the stack.
	 */
	public void push(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Value can not be null.");
		}
		
		this.collection.add(value);
	}
	
	/**
	 * Removes the object from top of the stack.
	 * @return Object that was removed(popped).
	 */
	public Object pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException("The stack is empty. pop() can not be executed.");
		}
		Object toReturn = this.peek();
		this.collection.remove(this.collection.size()-1);
		return toReturn;
	}
	
	/**
	 * Gets the object that is currently on top of the stack.
	 * @return Object from top of the stack.
	 */
	public Object peek() {
		if (!this.isEmpty()) {
			return this.collection.get(this.collection.size()-1);
		} else {
			throw new EmptyStackException("The stack is empty. peek() can not be executed.");
		}
	}
	
	/**
	 * Removes all objects from the stack.
	 */
	public void clear() {
		this.collection.clear();
	}
}
