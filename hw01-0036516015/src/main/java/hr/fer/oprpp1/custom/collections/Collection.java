package hr.fer.oprpp1.custom.collections;

/**Represents some general collection of objects
 * 
 * @author Zvonimir Petar Rezo
 *
 */
public class Collection {
	/**
	 * Constructor method
	 */
	protected Collection() {
		
	}
	
	/**
	 * 
	 * @return is the collection empty
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * 
	 * @return 0
	 */
	public int size() {
		return 0;
	}
	
	/**Adds given value to the collection
	 * Does nothing for now
	 * @param value
	 */
	public void add(Object value) {
		
	}
	
	/**Checks whether a collection contains given value.
	 * Only returns false for now.
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**Removes the given object if it's in the collection.
	 * For now just returns false.
	 * @param value
	 * @return true only if the collection contains given value
	 * as determined by equals method and removes it
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**Allocates new array with size equals to the
	 * size of the collection.
	 * For now just throws UnsupportedOperationException.
	 * 
	 * @return new array which contains collection elements
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**Calls processor.process() for each element
	 * of this collection.
	 * Empty method for now.
	 * 
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**Adds all elements from collection other to current collection
	 * 
	 * @param other-other collection
	 */
	public void addAll(Collection other) {
		class LocalProcessor extends Processor{
			
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}	
		}
		other.forEach(new LocalProcessor());
		
	}
	
	/**
	 * Removes all elements from current collection
	 * Empty method for now.
	 */
	public void clear() {
		
	}
	
	
}
