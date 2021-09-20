package hr.fer.oprpp1.custom.collections;
/**
 * Implementation of a dictionary. Maps keys to values.
 * @author Zvonimir Petar Rezo
 *
 * @param <K> - type of keys
 * @param <V> - type of values
 */
public class Dictionary<K,V> {
	private ArrayIndexedCollection<Element<K, V>> collection = new ArrayIndexedCollection<>();
	
	/**
	 * Private nested class implementing a dictionary element
	 * with key and value parameters.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	private static class Element<K, V> {
		private K key;
		private V value;
		
		/**
		 * Default constructor for the Element class.
		 * Takes two parameters.
		 * @param key - key of the element
		 * @param value - value associated to the key
		 * @throws NullPointterException 
		 */
		public Element(K key, V value) throws Exception {
			if (key == null) {
				throw new NullPointerException("Key can not be null.");
			}
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter for key.
		 * @return Key for current Element object.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Getter for value.
		 * @return Value for current Element object.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Setter for value.
		 * @param value - new value to be set
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Checks if the dictionary is empty.
	 * @return True if the dictionary is empty, false otherwise.
	 */
	boolean isEmpty() {
		return this.collection.size() == 0;
	}
	
	/**
	 * Getter for size of the dictionary.
	 * @return Size of the dictionary.
	 */
	int size() {
		return this.collection.size();
	}
	
	/**
	 * Deletes all elements from the dictionary.
	 */
	void clear() {
		this.collection.clear();
	}
	
	/**
	 * Inserts the element into the dictionary.
	 * @param key - key of element that needs to be added to the dictionary
	 * @param value - value of element that needs to be added to the dictionary
	 * @return If specified key was already in the dictionary
	 */
	V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		V returnValue;
		for (int i = 0; i < size(); i++) {
			if (collection.get(i).getKey().equals(key)) {
				returnValue = collection.get(i).getValue();
				collection.get(i).setValue(value);
				return returnValue;
			}
		}

		try {
			this.collection.add(new Element<K, V>(key, value));
		} catch (Exception e) {
			//Ignore
		}
		return null;

	}
	
	/**
	 * Gets the value associated with the given key.
	 * @param key - key to which the value is associated
	 * @return Value associated with the given key.
	 */
	V get(Object key) {
		for (int i = 0; i < this.size(); i++) {
			if (collection.get(i).getKey() == key) {
				return collection.get(i).getValue();
			}
		}
		return null;
	}
	
	/**
	 * Removes element with the given key.
	 * @param key - key of the element that needs to be removed from the dictionary
	 * @return Value of the key taht was removed from the dictionary.
	 */
	V remove(K key) {
		int index = -1;
		V returnValue = null;
		for (int i = 0; i < this.size(); i++) {
			if (collection.get(i).getKey() == key) {
				index = i;
				returnValue = collection.get(i).getValue();
				break;
			}
		}
		if (index != -1) {
			collection.remove(index);
		} 
		return returnValue;
		
		
	}
	
}
