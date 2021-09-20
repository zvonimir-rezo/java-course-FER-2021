package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of the token class.
 * Lexer returns objects of this class when running a lexical analysis.
 * @author Zvonimir Petar Rezo
 *
 */
public class Token {
	
	private TokenType type;
	private String value;
	
	/**
	 * Constructor of the Token class which takes two arguments.
	 * @param type - type of the token
	 * @param value - value of the token
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	
	/**
	 * Getter for the value of token.
	 * @return Value of current token.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Getter for the type of token.
	 * @return Type of current token.
	 */
	public TokenType getType() {
		return type;
	}
	
}
