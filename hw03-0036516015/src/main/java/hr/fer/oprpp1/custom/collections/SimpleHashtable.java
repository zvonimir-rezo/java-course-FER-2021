package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents implementation of a table. The table consists of objects
 * which have keys and values. Object can point to other objects so every
 * position in the table is actually a linked list.
 * 
 * @author Zvonimir Petar Rezo
 *
 * @param <K> - type of key
 * @param <V> - type of value
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	private TableEntry<K, V>[] table;
	int size;
	private int capacity = 16;
	private int modificationCount = 0;
	private int currentLowestPosition = capacity;

	public static void main(String[] args) {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());

	}

	/**
	 * Represents elements of the table. Every object has a key, a value for
	 * that key and a reference to next TableEntry in the list.
	 * 
	 * @author Zvonimir Petar Rezo
	 *
	 * @param <K> - type of key
	 * @param <V> - type of value
	 */
	public static class TableEntry<K, V> {
		K key;
		V value;
		TableEntry<K, V> next;

		/**
		 * Default constructor.
		 * 
		 * @param key   - value of the key
		 * @param value - value associated with the key
		 * @param next  - reference to next TableEntry
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for the key of current TableEntry.
		 * 
		 * @return Key of current TableEntry.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter for the value of current TableEntry.
		 * 
		 * @return Value of current TableEntry.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for the value of current TableEntry. Value given in arguments is new
		 * value of current TableEntry.
		 */
		public void setValue(V newValue) {
			value = newValue;
		}
	}

	/**
	 * An iterator used to iterate over the table.
	 * 
	 * @author Zvonimir Petar Rezo
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		private TableEntry<K, V> next;
		private TableEntry<K, V> last;
		private int index = 0;
		private int count = size();
		private int internalModificationCount = modificationCount;

		/**
		 * Checks if the collection has next element to iterate over.
		 * @return True if the collection has next element to iterate over, false otherwise.
		 * @throws ConcurrentModificationException if the table was changed unexpectedly while iterating.
		 */
		@Override
		public boolean hasNext() {
			if (internalModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			return count > 0;
		}

		/**
		 * Gets the next element of the collection.
		 * @return TableEntry object which is the next element of the collection.
		 * @throws ConcurrentModificationException if the table was changed unexpectedly while iterating.
		 * @throws NoSuchElementException if the end is reached.
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (internalModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			if (count == 0)
				throw new NoSuchElementException();
			count--;
			TableEntry<K, V> entry = next;

			while (entry == null)
				entry = table[index++];

			next = entry.next;
			last = entry;
			return entry;

		}
		
		/**
		 * Removes current element from the table.
		 * @throws ConcurrentModificationException if the table was changed unexpectedly while iterating.
		 * @throws IllegalStateException if current element is null, can occur if remove is called more than
		 * once for current entry.
		 */
		@Override
		public void remove() {
			if (internalModificationCount != modificationCount)
				throw new ConcurrentModificationException();
			if (last == null)
				throw new IllegalStateException();
			SimpleHashtable.this.remove(last.key);
			last = null;
			internalModificationCount++;
		}
	}

	/**
	 * Default constructor. Takes no arguments and sets the capacity of the table to
	 * default value(16).
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry[]) Array.newInstance(TableEntry.class, capacity);
	}

	/**
	 * Constructor which takes one argument of capacity and sets the capacity of the
	 * table to that value.
	 * 
	 * @param capacity - value of the capacity table needs to be set to when
	 *                 constructing the object
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		int nextPowerTwo = 1;
		while (nextPowerTwo < capacity) {
			nextPowerTwo *= 2;
		}
		table = (TableEntry[]) Array.newInstance(TableEntry.class, nextPowerTwo);
		this.capacity = nextPowerTwo;
	}

	/**
	 * Puts the TableEntry with given key-value pair into the table. Position of
	 * TableEntry is determined by hash code of its key and it is always placed at
	 * the last place in linked list at that position. If the key already is in the
	 * table, its value is replaced by the value given in arguments.
	 * 
	 * @param key   - key of the entry
	 * @param value - value of the entry
	 * @return Returns old value if the value was overwritten, null otherwise.
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("Key can't be null");
		}
		if (size / capacity >= 0.75) {
			TableEntry<K, V>[] tmp = this.toArray();
			capacity *= 2;
			currentLowestPosition = capacity;
			table = (TableEntry<K, V>[]) Array.newInstance(TableEntry.class, capacity);
			size = 0;
			for (TableEntry<K, V> te : tmp) {
				this.put(te.getKey(), te.getValue());
			}
		}

		V rtnValue = null;
		int position = Math.abs(key.hashCode()) % (capacity);
		TableEntry<K, V> toInsert = new TableEntry<K, V>(key, value, null);
		if (table[position] == null) {
			table[position] = toInsert;
			if (position < currentLowestPosition) {
				currentLowestPosition = position;
			}
		} else {
			TableEntry<K, V> current = table[position];
			while (current.next != null) {
				if (current.getKey() == key) {
					rtnValue = current.getValue();
					current.setValue(value);
					return rtnValue;
				}
				current = current.next;
			}
			current.next = toInsert;
		}
		modificationCount++;
		size++;
		return null;
	}

	/**
	 * Getter for the value associated with the key specified in arguments of the
	 * method.
	 * 
	 * @param key - key to search for in the table
	 * @return Value associated with the given key, if that key does not exist in
	 *         this table, returns null.
	 */
	public V get(Object key) {
		int position = key.hashCode() % (capacity);
		TableEntry<K, V> current = table[position];
		while (current != null) {
			if (current.getKey().equals(key)) {
				return current.getValue();
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Gets the number of elements in the table.
	 * 
	 * @return Number of elements in the table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks if the table contains an entry with the key specified in arguments.
	 * 
	 * @param key - key to search for in the table
	 * @return True if the key is found, false otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new NullPointerException("Key can not be null.");
		}
		int position = key.hashCode() % (capacity);
		TableEntry<K, V> current = table[position];
		while (current != null) {
			if (current.getKey().equals(key)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Checks if the table contains an entry with the value specified in arguments.
	 * 
	 * @param value - value to search for in the table
	 * @return True if the value is found, false otherwise.
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < capacity; i++) {
			TableEntry<K, V> current = table[i];
			while (current != null) {
				if (current.getValue().equals(value)) {
					return true;
				}
				current = current.next;
			}
		}
		return false;
	}

	/**
	 * Removes the object from the table with key equal to the one specified in
	 * arguments.
	 * 
	 * @param key - key of the object that should be removed, might not be found
	 * @return Value of the removed object. Returns null if the object was not
	 *         found.
	 */
	public V remove(Object key) {
		if (key == null)
			return null;
		int position = Math.abs(key.hashCode() % (capacity));
		TableEntry<K, V> current = table[position];
		TableEntry<K, V> prev = current;
		V value;
		while (current != null) {
			if (current.getKey().equals(key)) {
				prev.next = current.next;
				value = current.getValue();
				current = null;
				size--;
				modificationCount++;
				return value;
			}
			prev = current;
			current = current.next;
		}
		modificationCount++;
		return null;
	}

	/**
	 * Clears the whole table by setting every +thing to null;
	 */
	public void clear() {
		for (int i = 0; i < capacity; i++) {
			table[i] = null;
		}
	}

	/**
	 * Checks if the table is empty.
	 * 
	 * @return True if the table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Converts the object to a convenient string to print out.
	 * 
	 * @return String representation of the object.
	 */
	public String toString() {
		if (size() == 0)
			return "";
		String str = "[";

		TableEntry<K, V>[] arr = this.toArray();
		int i;
		for (i = 0; i < size() - 1; i++) {
			str += (arr[i].getKey() + "=" + arr[i].getValue() + ", ");
		}
		return str + (arr[i].getKey() + "=" + arr[i].getValue() + "]");
	}

	/**
	 * Moves all elements of the table to an array.
	 * 
	 * @return Array consisting of all elements of the table.
	 */
	public TableEntry<K, V>[] toArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] arr = (TableEntry[]) Array.newInstance(TableEntry.class, size());
		int pos = 0;
		for (int i = 0; i < this.capacity; i++) {
			TableEntry<K, V> current = table[i];
			while (current != null) {
				arr[pos++] = current;
				current = current.next;
			}
		}
		return arr;
	}
	
	/**
	 * Makes instance of an iterator for iterating over SimpleHashTable.
	 * @return Iterator object used to iterate over current SimpleHashTable.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
