package hr.fer.oprpp1.custom.collections;

/**Represents some general collection of objects
 * 
 * @author Zvonimir Petar Rezo
 *
 */
public interface Collection {
	
	/**
	 * 
	 * @return True if the collection is empty, false otherwise
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Calculates and returns size of the collection.
	 * @return Size of the collection
	 */
	int size();
	
	/**Adds given value to the collection
	 * @param value - object which must be added to the collection
	 */
	void add(Object value);
	
	/**Checks whether a collection contains given value.
	 * 
	 * @param value - the value to search for
	 * @return True if collection contains the value, false otherwise.
	 */
	boolean contains(Object value);
	
	/**Removes the given object if it's in the collection.
	 * @param value - object to be removed
	 * @return true only if the collection contains given value
	 * as determined by equals method and the method removes it
	 */
	boolean remove(Object value);
	
	/**Allocates new array with size equals to the
	 * size of the collection.
	 * 
	 * @return New array which contains collection elements
	 */
	Object[] toArray();
	
	/**Calls processor.process() for each element
	 * of this collection.
	 * 
	 * @param processor - object of class Processor which is used to
	 * perform processing  on elements of the collection
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			Object el = getter.getNextElement();
			processor.process(el);
		}
	}
	
	/**Adds all elements from collection other to current collection
	 * 
	 * @param other - collection whose elements need to be added to current collection
	 */
	default void addAll(Collection other) {
		class LocalProcessor implements Processor{
			
			public void process(Object value) {
				Collection.this.add(value);
			}	
		}
		other.forEach(new LocalProcessor());
		
	}
	
	/**
	 * Removes all elements from current collection.
	 */
	void clear();
	
	/**
	 * Creates a new ElementsGetter object.
	 * @return New ElementsGetter object for the current collection.
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Adds all elements of the given collection that satisfy the condition given by the tester
	 * to the current collection.
	 * @param col - collection whose elements need to be tested and then added if they pass the test
	 * @param tester - tester object used to test if elements should be added to current collection
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while(getter.hasNextElement()) {
			Object el = getter.getNextElement();
			if (tester.test(el)) {
				this.add(el);
			}
		}
		
	}
	
}
