package hr.fer.zemris.lsystems.impl;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**Implementation of resizable array-backed
 * collection of objects
 * 
 * @author Zvonimir Petar Rezo
 *
 */

public class ArrayIndexedCollection<T> implements List<T> {
	
	private static final int capacity = 16;
	private int size = 0;
	private Object[] elements;
	private long modificationCount = 0;
	
	private static class Getter<E> implements ElementsGetter<E> {
		
		private int current = 0;
		private ArrayIndexedCollection<? extends E> col;
		private long savedModificationCount;
		
		public Getter(ArrayIndexedCollection<? extends E> col) {
			this.col = col;
			this.savedModificationCount = col.modificationCount;
		}
		
		public boolean hasNextElement() {
			if (this.savedModificationCount != this.col.modificationCount) {
				throw new ConcurrentModificationException("You can't change contents of the collection while using ElementsGetter.");
			}
			if (this.current < this.col.size) {
				return true;
			}
			return false;
		}
		
		public E getNextElement() {
			if (this.savedModificationCount != this.col.modificationCount) {
				throw new ConcurrentModificationException("You can't change contents of the collection while using ElementsGetter.");
			}
			if (this.current < this.col.size) {
				return this.col.get(this.current++);
			}
			throw new NoSuchElementException("The collection does not contain any more elements.");
		}
		
	}
	
	
	/**
	 * Default constructor.
	 */
	public ArrayIndexedCollection() {
		this(capacity);
	}
	
	/**
	 * Constructor which takes integer argument of initialCapacity.
	 * @param initialCapacity - initial capacity for the elements array
	 * @throws IllegalArgumentException() if initialCapacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity must be greater than 0.");
		}
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Constructor which takes another collection for an argument.
	 * Adds all elements of the other collection to current collection.
	 * @param other - Collection
	 * @throws NullPointerException() if argument is null
	 */
	public ArrayIndexedCollection(Collection<T> other) {
		this(0, other);
	}
	
	/**
	 * Constructor which takes two arguments.
	 * If initialCapacity is lower than the size of collection given in arguments,
	 * the size of the given collection will be used for elements array preallocation.
	 * This constructor then adds all elements of given collection to
	 * current collection. 
	 * 
	 * @param initialCapacity - initial capacity
	 * @param other - Collection()
	 * @throws NullPointerException if the given collection is null
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection<T> other) {		
		if(other == null) {
			throw new NullPointerException();
		}
		
		elements = other.toArray();
		size = elements.length;

		this.addAll(other);
		this.size = other.size();
	}


	/**
	 * Size getter.
	 * @return Size of the collection
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Gets the elements array.
	 * @return Array of elements.
	 */
	@SuppressWarnings("unchecked")
	public T[] getElements() {
		return (T[]) this.elements;
	}
	
	/**
	 * Adds the given object into this collection(reference is added
	 * into first empty place in the elements array).
	 * If the elements array is full, it should be reallocated by
	 * doubling its size.
	 * @param value - object which needs to be added
	 * @throws NullPointerException if value == null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Given object is null.");
		}
		
		if (this.size() == this.elements.length) {
			this.elements = this.doubleSizeArray();
		}
		
		this.modificationCount++;
		this.elements[this.size++] = value;

	}
	
	/**
	 * Checks whether a collection contains a value.
	 * @return True if collection contains given value, false otherwise.
	 */
	@Override
	public boolean contains(Object value) {
		boolean result = false;
		for (int i = 0; i < this.size(); i++) {
			if (this.elements[i].equals(value)) {
				result = true;
			}
		}
		
		return result;
	}

	/**
	 * Gives the element array.
	 * @return elements array
	 */
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		ArrayIndexedCollection<T> col = new ArrayIndexedCollection<T>();
		for (T elem : a) {
			col.add(elem);
		}
		return col.getElements();
	}


	/**
	 * Doubles the array size.
	 * @return Array with doubled size.
	 */
	Object[] doubleSizeArray() {
		Object[] tmp = new Object[2*this.size()];
		System.arraycopy(this.elements, 0, tmp, 0, this.size());
		return tmp;
	}
	
	/**
	 * Returns the object stored at position index.
	 * @param index - position
	 * @return Object at a given index.
	 * @throws IndexOutOfBoundsException if index is not in interval
	 * [0, this.size()-1].
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if(index < 0 || index > this.size()-1) {
			throw new IndexOutOfBoundsException("Index should be between ");
		}
		
		return (T) this.elements[index];
	}
	
	/**
	 * Removes all elements from the collection.
	 * Overrides Collection method clear.
	 */
	@Override
	 public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.modificationCount++;
		this.size = 0;
	}
	
	/**
	 * Inserts the given value at the given position in array.
	 * If the array is full, doubles its size and then inserts value.
	 * @param value - value that needs to be inserted
	 * @param position - position in which the value needs to be inserted
	 * @throws IndexOutOfBoundsException if position is not in interval
	 * [0, this.size()]
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		if (this.size() == this.elements.length) {
			this.elements = this.doubleSizeArray();
		}
		int i = this.size()-1;
		for(; i >= position; i--) {
			this.elements[i+1] = this.elements[i];
		}
		this.elements[i+1] = this.elements[i];
		this.elements[i] = value;
		this.modificationCount++;
		this.size++;

	}
	
	/**
	 * Searches the collection for given value and returns the index
	 * of the first occurrence of value.
	 * @param value - value the method is searching for
	 * @return index of the first occurrence of the given value,
	 * -1 if the value isn't found
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < this.size(); i++) {
			if(this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at the given index.
	 * @param index - given index
	 * @throws IndexOutOfBoundsException if index is not in interval
	 * [0, this.size()-1]
	 */
	public void remove(int index) {
		if(index < 0 || index > this.size() - 1) {
			throw new IndexOutOfBoundsException();
		}
//		if (index == this.size()) {
//			this.elements[index] = null;
//		}
//		for(int i = index; i < this.size()-1; i++) {
//			this.elements[i] = this.elements[i+1];
//		}
		for(int i=index;i<size&&elements[i]!=null;i++) {
            elements[i]=elements[i+1];
        }
		this.modificationCount++;
		this.size--;
	}
	
	/**
	 * Removes element with the given value.
	 * @param value - value of the element that needs to be removed
	 * @return True if the object was removed, false otherwise.
	 */
	@Override
	public boolean remove(Object value) {
		if (!this.contains(value)) {
			return false;
		}
		int i;
		for(i = 0; i < elements.length; i++) {
			if (this.elements[i].equals(value)) {
				break;
			}
		}
		while (i < elements.length-1) {
			this.elements[i] = this.elements[i+1];
			i++;
		}
		this.modificationCount++;
		this.size--;
		return true;

		
	}
	
	/**
	 * Creates ElementsGetter object used to get object of
	 * the current collection.
	 * @return Getter object.
	 */
	public ElementsGetter<T> createElementsGetter() {
		return new Getter<T>(this);
	}

	
	/**
	 * Moves all elements of the collection to an array.
	 * @return Array which consists of all elements of the collection.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}


	

}
