package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of a lexer. Runs lexical analysis on the given text.
 * 
 * @author Zvonimir Petar Rezo
 *
 */
public class QueryLexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	LexerState state;

	/**
	 * Constructor which converts text to character array.
	 * Sets lexer state to normal.
	 * @param text - text which needs to be analyzed
	 */
	public QueryLexer(String text) {
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.NORMAL;
	}

	/**
	 * Calls extractNextToken() which gives this method the next token object.
	 * 
	 * @return Next token from the data.
	 */
	public Token nextToken() {
		this.extractNextToken();
		return this.token;
	}

	/**
	 * Calling this method gives back the current token. Doesn't generate next
	 * token.
	 * 
	 * @return Current token of this lexer object.
	 */
	public Token getToken() {
		return this.token;
	}

	/**
	 * Extract next token from the data array and stores the token in current token
	 * of the lexer.
	 */
	public void extractNextToken() {
		// Removal of spaces
		while (currentIndex < data.length) {
			char crnt = data[currentIndex];
			if (crnt == ' ' || crnt == '\t' || crnt == '\r' || crnt == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOL, "end of line");
			return;
		}

		if (state == LexerState.NORMAL) {
			if (Character.isLetter(data[currentIndex])) {
				String str = "";
				str += data[currentIndex++];
				while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
					str += data[currentIndex++];
				}
				if (str.equals("LIKE")) {
					state = LexerState.LIKE;
					token = new Token(TokenType.OPERATOR, str);
				} else if (str.toUpperCase().equals("AND")) {
					token = new Token(TokenType.AND, str);
				} else {
					token = new Token(TokenType.WORD, str);
				}
				return;
			}

			if (Character.valueOf(data[currentIndex]).equals('<')) {
				currentIndex++;
				if (Character.valueOf(data[currentIndex]).equals('=')) {
					currentIndex++;
					token = new Token(TokenType.OPERATOR, "<=");
				} else {
					token = new Token(TokenType.OPERATOR, "<");
				}
				return;
			}

			if (Character.valueOf(data[currentIndex]).equals('>')) {
				currentIndex++;
				if (Character.valueOf(data[currentIndex]).equals('=')) {
					currentIndex++;
					token = new Token(TokenType.OPERATOR, ">=");
				} else {
					token = new Token(TokenType.OPERATOR, ">");
				}
				return;
			}

			if (Character.valueOf(data[currentIndex]).equals('=')) {
				token = new Token(TokenType.OPERATOR, "=");
				currentIndex++;
				return;
			}

			if (Character.valueOf(data[currentIndex]).equals('!')) {
				currentIndex++;
				if (Character.valueOf(data[currentIndex]).equals('=')) {
					currentIndex++;
					token = new Token(TokenType.OPERATOR, "!=");
				} else {
					throw new QueryLexerException("Invalid operator");
				}
				return;
			}

			if (Character.valueOf(data[currentIndex]).equals('"')) {
				String str = "";
				currentIndex++;
				while (currentIndex < data.length && !(Character.valueOf(data[currentIndex]).equals('"'))) {
					str += data[currentIndex++];
				}

				token = new Token(TokenType.STRING, str);
				currentIndex++;
				return;
			}
			
		} else {
			if (Character.valueOf(data[currentIndex]).equals('"')) {
				String str = "";
				int asteriskCounter = 0;
				currentIndex++;
				while (currentIndex < data.length && !(Character.valueOf(data[currentIndex]).equals('"'))) {
					if (Character.valueOf(data[currentIndex]).equals('*')) {
						asteriskCounter++;
						if (asteriskCounter > 1) {
							throw new IllegalArgumentException();
						}
					}
					str += data[currentIndex++];
				}
				state = LexerState.NORMAL;
				token = new Token(TokenType.STRING, str);
				currentIndex++;
				return;
			}
		}

	}

	public void setCurrentIndex(int index) {
		currentIndex = index;
	}

}
