package hr.fer.oprpp1.custom.collections;

/**
 * Represents a processor which has method process.
 * @author Zvonimir Petar Rezo
 *
 */
public interface Processor {
	
	/**
	 * Processes the given object.
	 * @param value - object that needs to be processed
	 */
	void process(Object value);

}

