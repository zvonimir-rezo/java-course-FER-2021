package hr.fer.zemris.java.hw06.shell;

/**
 * Exception that should be thrown on failure while reading or writing.
 * @author Zvonimir Petar Rezo
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Default constructor.
	 * @param message - message to be printed out
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
