package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of a lexer. Runs lexical analysis on the given text.
 * @author Zvonimir Petar Rezo
 *
 */
public class Lexer {
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState currentState;
	
	/**
	 * Constructor which converts text to character array.
	 * @param text - text which needs to be analyzed 
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.currentState = LexerState.BASIC;
	}
	
	/**
	 * Sets the state of the current lexer.
	 * @param state - state from enumeration LexerState
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("Please enter a valid state.");
		this.currentState = state;
	}
	

	/**
	 * Calls extractNextToken() which gives this method the next token object.
	 * @return Next token from the data.
	 */
	public Token nextToken() {
		this.extractNextToken();
		return this.token;
	}

	/**
	 * Calling this method gives back the current token.
	 * Doesn't generate next token.
	 * @return Current token of this Lexer object.
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Extract next token from the data array and stores the token in
	 * current token of the lexer.
	 */
	public void extractNextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("You have reached the end of text.");
		}
		//Removal of spaces
		while (currentIndex < data.length) {
			char crnt = data[currentIndex];
			if (crnt == ' ' || crnt == '\t' || crnt == '\r' || crnt == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return;
		}
		
		//Ako je LexerState BASIC
		if (this.currentState == LexerState.BASIC) {
			
			
			if (data[currentIndex] == '\\') {
				String str = "";
				currentIndex++;
				if (currentIndex >= data.length) throw new LexerException("Escape sign is at the end of data.");
				
				if (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])) {
					str += data[currentIndex++];
					while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t' && data[currentIndex] != '\r' && data[currentIndex] != '\n') {
						
						if (Character.isLetter(data[currentIndex])) {
							str += data[currentIndex++];
						}
						else if (data[currentIndex] == '\\') {
							currentIndex++;
							if (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])) {
								str += data[currentIndex++];
								continue;
							}
						} else {
							break;
						}
					}
					token = new Token(TokenType.WORD, str);
					return;
				} 
				throw new LexerException("Invalid input.");

			}
			
			
			if (Character.isLetter(data[currentIndex])) {
				String str = "";
				str += data[currentIndex++];
				while (currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\t' && data[currentIndex] != '\r' && data[currentIndex] != '\n') {
					if (Character.isLetter(data[currentIndex])) {
						str += data[currentIndex++];
					}
					else if (data[currentIndex] == '\\') {
						currentIndex++;
						if (data[currentIndex] == '\\' || Character.isDigit(data[currentIndex])) {
							str += data[currentIndex++];
							continue;
						}
					} else {
						break;
					}
				}
				token = new Token(TokenType.WORD, str);
				return;
			}
			
			if ("#".indexOf(Character.valueOf(data[currentIndex])) != -1) {
				token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
				currentIndex++;
				this.setState(LexerState.EXTENDED);
				return;
			}
			
			if ("+-()=,;:.?!".indexOf(Character.valueOf(data[currentIndex])) != -1 ) {
				token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
				currentIndex++;
				return;
			}
			
			
			if (Character.isDigit(Character.valueOf(data[currentIndex]))) {
				String str = "";
				str += data[currentIndex++];
				while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
					str += data[currentIndex++];
				}
				try {
					token = new Token(TokenType.NUMBER, Long.parseLong(str));
				} catch (NumberFormatException ex) {
					throw new LexerException(ex.getMessage());
				}
				return;
			}
		//ako je LexerState EXTENDED	
		} else {	
			
			if ("#".indexOf(Character.valueOf(data[currentIndex])) != -1) {
				token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex]));
				currentIndex++;
				this.setState(LexerState.BASIC);
				return;
			}
			String str = "";
			while(currentIndex < data.length && data[currentIndex] != ' ') {
				if ("#".indexOf(Character.valueOf(data[currentIndex])) != -1) {
					token = new Token(TokenType.WORD, str);
					return;
				}
				str += data[currentIndex++];
			}
			
			if (str == "") {
				throw new LexerException("Data can not be empty.");
			}
			token = new Token(TokenType.WORD, str);
			return;
		}
		
		
		
	}
	
	
	
}
