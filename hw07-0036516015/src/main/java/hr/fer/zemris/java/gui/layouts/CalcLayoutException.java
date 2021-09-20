package hr.fer.zemris.java.gui.layouts;

/**
 * Implementation of an exception that is often thrown in our calculator.
 * Extends RuntimeException.
 * @author Zvonimir Petar Rezo
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Default constructor. Prints the error message.
	 * @param message - message that is printed
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
}
