package hr.fer.oprpp1.custom.scripting.lexer;


/**
 * SmartScriptLexerException class. Extends RuntimeException.
 * Used to handle expected exceptions that occur in a lexer.
 * @author Zvonimir Petar Rezo
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Default constructor.
	 * @param message - message that the exception displays.
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
	
}
