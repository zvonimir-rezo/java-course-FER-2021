package hr.fer.zemris.lsystems.impl;

/**
 * Represents a processor which has method process.
 * @author Zvonimir Petar Rezo
 *
 */
public interface Processor<T> {
	
	/**
	 * Processes the given object.
	 * @param value - object that needs to be processed
	 */
	void process(T value);


}

