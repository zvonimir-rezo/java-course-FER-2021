package hr.fer.oprpp1.custom.collections;


/**
 * Implementation of linked list collection of objects.
 * 
 * @author Zvonimir Petar Rezo
 * 
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Private static class which represents element of the collection.
	 * 
	 */
	private static class ListNode {
		ListNode prev;
		ListNode next;
		Object value;

		public ListNode(Object value) {
			this.value = value;
		}

	}

	private int size;
	private ListNode first;
	private ListNode last;


	/**
	 * Default constructor. Creates an empty collection so that first and last
	 * element are null.
	 */
	public LinkedListIndexedCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * Constructor which adds all elements of the collection given in parameters to
	 * the current collection.
	 * 
	 * @param other - references other collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);
	}

	/**
	 * Getter for the collection size.
	 * 
	 * @return Size of the collection.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Converts all elements of the collection to an array;
	 * 
	 * @return Array which contains all elements of the collection.
	 */
	@Override
	public Object[] toArray() {
		Object[] array = new Object[this.size];
		ListNode ln = this.first;
		int counter = 0;
		while (ln != null) {
			array[counter] = ln.value;
			ln = ln.next;
			counter++;
		}
		return array;
	}

	/**
	 * Calls processor.process on every element of the collection.
	 * 
	 * @param processor - object of class Processor
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode i = this.first;
		int counter = 0;
		while (counter < this.size) {
			processor.process(i.value);
			i = i.next;
			counter++;
		}
	}

	/**
	 * Adds the given object to this collection.
	 * 
	 * @throws IllegalArgumentException if given object is null reference.
	 * @param value - object that needs to be added to the collection
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value cannot be null.");
		}

		ListNode newNode = new ListNode(value);

		if (this.first == null) {
			this.first = newNode;
		}
		if (this.last == null) {
			this.last = newNode;
		}

		this.last.next = newNode;
		newNode.prev = this.last;
		this.last = newNode;

		this.size++;
	}

	/**
	 * Searches and returns the object at a given position.
	 * 
	 * @param index - position
	 * @return Object that is stored in the collection at position index.
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index has to be between 0 and size-1");
		}

		if (index == 0) {
			return this.first.value;
		}

		if (index == this.size - 1) {
			return this.last.value;
		}

		ListNode node;
		int i;
		if (index < this.size / 2) {
			node = this.first;
			i = 0;
			while (i != index) {
				node = node.next;
				i++;
			}
		} else {
			node = this.last;
			i = this.size - 1;
			while (i != index) {
				node = node.prev;
				i--;
			}
		}

		return node.value;
	}

	/**
	 * Checks if the collection contains an object.
	 * 
	 * @param value - object whose existance in collection has to be checked
	 * @return True if collection contains the object, false otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		ListNode node = this.first;
		while (node != null) {
			if (node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}

	/**
	 * Removes all elements from a collection.
	 */
	@Override
	public void clear() {
		ListNode node = this.first;
		while (node.next != null) {
			node = node.next;
			node.value = null;

		}
		this.first.value = null;
		this.first.next = null;
		this.last.prev = null;
		this.size = 0;
	}

	/**
	 * Inserts given object to a given position in the collection.
	 * 
	 * @param value    - object that needs to be inserted
	 * @param position - position at which the object needs to be inserted
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index has to be between 0 and size");
		}

		if (value == null) {
			throw new IllegalArgumentException("Object can not be null");
		}

		ListNode newNode = new ListNode(value);
		newNode.next = null;
		newNode.prev = null;

		if (this.first == null) {
			this.first = newNode;
			this.last = newNode;
			return;
		}

		ListNode node;
		int i;
		if (position < Math.floor(this.size / 2)) {
			node = this.first;
			i = 0;
			while (i != position) {
				node = node.next;
				i++;
			}
		} else {
			node = this.last;
			i = this.size - 1;
			while (i != position) {
				node = node.prev;
				i--;
			}
		}

		newNode.next = node;
		newNode.prev = node.prev;
		node.prev.next = newNode;
		node.prev = newNode;
		this.size++;

	}

	/**
	 * Searches for a given object in the collection.
	 * 
	 * @param value - object to search for
	 * @return Position of the given object in this collection, -1 if the object
	 *         value is not found.
	 */
	public int indexOf(Object value) {
		ListNode node = this.first;
		int index = 0;
		while (node.value != value && node.next != null) {
			node = node.next;
			index++;
		}

		return index >= this.size ? -1 : index;
	}

	/**
	 * Removes an element of the collection at the given position.
	 * 
	 * @param index - position at which stands the element which needs to be removed
	 * @throws IndexOutOfBoundsException if the index is not between 0 and size-1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index has to be between 0 and size-1");
		}

		if (index == this.size - 1) {
			this.last.prev.next = null;
			this.last = null;
			this.size--;
			return;
		}

		ListNode node;
		int i;
		if (index < this.size / 2) {
			node = this.first;
			i = 0;
			while (i != index) {
				node = node.next;
				i++;
			}
		} else {
			node = this.last;
			i = this.size - 1;
			while (i != index) {
				node = node.prev;
				i--;
			}
		}
		node.prev.next = node.next;
		node.next.prev = node.prev;
		node = node.next;
		this.size--;
	}

}
