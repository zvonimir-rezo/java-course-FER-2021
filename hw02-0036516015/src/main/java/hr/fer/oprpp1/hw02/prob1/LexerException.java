package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of LexerException.
 * Extends RuntimeException. Used in the lexer to handle exceptions.
 * @author Zvonimir Petar Rezo
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Constructor for the lexer exception.
	 * @param message - message given in arguments that should be printed out
	 */
	public LexerException(String message) {
		super(message);
	}
	
}
