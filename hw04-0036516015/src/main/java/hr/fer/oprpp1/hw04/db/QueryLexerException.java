package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of an exception that could be thrown in QueryLexer class.
 * Extends RuntimeException
 * @author Zvonimir Petar Rezo
 *
 */
public class QueryLexerException extends RuntimeException {
	/**
	 * Constructor which takes in one argument of message.
	 * @param message - message to print out
	 */
	public QueryLexerException(String message) {
		super(message);
	}
}
