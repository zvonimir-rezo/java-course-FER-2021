package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Implementation of linked list collection of objects.
 * 
 * @author Zvonimir Petar Rezo
 * 
 */
public class LinkedListIndexedCollection<T> implements List<T> {

	/**
	 * Private static class which represents element of the collection.
	 * 
	 */
	private static class ListNode<T> {
		ListNode<T> prev;
		ListNode<T> next;
		T value;

		public ListNode(T value) {
			this.value = value;
		}

	}
	
	/**
	 * Implementation of getter which is used to get elements of the collection.
	 * Implements ElementsGetter.
	 * @author Zvonimir Petar Rezo
	 *
	 * @param <E> - type of elements
	 */
	private static class Getter<E> implements ElementsGetter<E> {
		
		private ListNode<? extends E> current;
		private LinkedListIndexedCollection<? extends E> col;
		private long savedModificationCount = 0;
		
		/**
		 * Default constructor. Takes one argument.
		 * @param col - collection which the getter will run on
		 */
		public Getter(LinkedListIndexedCollection<? extends E> col) {
			this.col = col;
			this.current = col.first;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Checks if the getter reached the end or has more elements.
		 * @return True if getter has more elements to get, false otherwise.
		 * @throws ConcurrentModificationException if content of the collection
		 * was modified while using the getter.
		 */
		public boolean hasNextElement() {
			if (this.savedModificationCount != this.col.modificationCount) {
				throw new ConcurrentModificationException("You can't change contents of the collection while using ElementsGetter.");
			}
			if (this.current != null) {
				return true;
			}
			return false;
		}
		
		/**
		 * Gets next element of the collection.
		 * @return Value of the element.
		 * @throws ConcurrentModificationException if content of the collection
		 * was modified while using the getter.
		 * @throws NoSuchElementException if the collection does not contain any more elements.
		 */
		public E getNextElement() {
			if (this.savedModificationCount != this.col.modificationCount) {
				throw new ConcurrentModificationException("You can't change contents of the collection while using ElementsGetter.");
			}
			if (this.current != null) {
				ListNode<? extends E> tmp = this.current;
				this.current = this.current.next;
				return (E) tmp.value;
			}
			throw new NoSuchElementException("The collection does not contain any more elements.");
		}
		
	}

	private int size;
	private ListNode<T> first;
	private ListNode<T> last;
	private long modificationCount = 0;

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
	public LinkedListIndexedCollection(Collection<T> other) {
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
		ListNode<T> ln = this.first;
		int counter = 0;
		while (ln != null) {
			array[counter] = ln.value;
			ln = ln.next;
			counter++;
		}
		return array;
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
			throw new IllegalArgumentException("Object can not be null");
		}

		ListNode<T> newNode = new ListNode(value);

		if (this.first == null) {
			this.first = newNode;
			this.last = newNode;
			this.first.next = null;
			this.first.prev = null;
			this.last.next = null;
			this.last.prev = null;
			this.modificationCount++;
			this.size++;
			return;
		}

		this.last.next = newNode;
		newNode.prev = this.last;
		this.last = newNode;
		this.modificationCount++;
		this.size++;
	}

	/**
	 * Searches and returns the object at a given position.
	 * 
	 * @param index - position
	 * @return Object that is stored in the collection at position index.
	 */
	public T get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException("Index has to be between 0 and size-1");
		}

		if (index == 0) {
			return (T) this.first.value;
		}

		if (index == this.size - 1) {
			return (T) this.last.value;
		}

		ListNode<T> node;
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

		return (T) node.value;
	}

	/**
	 * Checks if the collection contains an object.
	 * 
	 * @param value - object whose existance in collection has to be checked
	 * @return True if collection contains the object, false otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		ListNode<T> node = this.first;
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
		ListNode<T> node = this.first;
		while (node.next != null) {
			node = node.next;
			node.value = null;

		}
		this.first.value = null;
		this.first.next = null;
		this.last.prev = null;
		this.modificationCount++;
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

		ListNode<T> newNode = new ListNode(value);
		newNode.next = null;
		newNode.prev = null;

		if (this.first == null) {
			this.first = newNode;
			this.last = newNode;
			return;
		}

		ListNode<T> node;
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
		this.modificationCount++;
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
		ListNode<T> node = this.first;
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

		ListNode<T> node;
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
		this.modificationCount++;
		this.size--;
	}
	
	/**
	 * Removes object with the given value.
	 * @return True if the object was removed, false otherwise.
	 */
	@Override
	public boolean remove(Object value) {
		ListNode<T> node = first;
		while (node != null) {
			if (node.value.equals(value)) {
				node.prev.next = node.next;
				node.next.prev = node.prev;
				node = node.next;
				modificationCount++;
				size--;
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Creates a new ElementsGetter object.
	 * @return The created ElementsGetter object.
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new Getter<T>(this);
	}
	
	/**
	 * Fills the given array with elements of the collection.
	 * @param a - array that needs to be filled
	 * @return Array filled with elements of the collection.
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	@Override
	public <T> T[] toArray(T[] a) {
		a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
		ListNode<T> ln = (ListNode<T>) first;
		for ( int i = 0; i < size; i++) {
			a[i] = (T) ln.value;
			ln = ln.next;
		}
		return a;
		
	}


}
