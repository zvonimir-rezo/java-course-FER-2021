package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Implementation of SmartScriptParserException.
 * Extends RuntimeException. Used for handling expected exceptions in the parser.
 * @author Zvonimir Petar Rezo
 *
 */
public class SmartScriptParserException extends RuntimeException {

	public SmartScriptParserException(String message) {
		super(message);
	}
	
}
