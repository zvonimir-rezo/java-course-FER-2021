package hr.fer.oprpp1.custom.collections;

/**
 * Interface which provides methods for checking state of collection getter.
 * @author Zvonimir Petar Rezo
 *
 */
public interface ElementsGetter {
	
	/**
	 * Checks if the collection has elements left.
	 * @return True if the collection still has elements, false otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * If the collection has next elements, returns it.
	 * @return Next element of the collection.
	 */
	Object getNextElement();
	
	/**
	 * Processes the remaining elements of a getter.
	 * @param p - processor whose method process will be used
	 * on the remaining elements
	 */
	default void processRemaining(Processor p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
	
}
