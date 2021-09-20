package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementOperator;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * The lexer class. Runs lexical analysis on the given text.
 * @author Zvonimir Petar Rezo
 *
 */
public class SmartScriptLexer {
	private char[] data; // ulazni tekst
	private Element token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState currentState;


	/**
	 * Constructor which converts text to character array.
	 * @param text - text which needs to be analyzed
	 */
	public SmartScriptLexer(String text) {
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.currentState = LexerState.TEXT;
	}
	
	/**
	 * Setter for the state value.
	 * Current state of the parser becomes the state entered in arguments.
	 */
	public void setState(LexerState state) {
		this.currentState = state;
	}
	
	/**
	 * Lexer gets next token of the text it runs lexical analysis on.
	 * @return Next token of class Element.
	 */
	public Element nextToken() {
		this.extractNextToken();
		return this.token;
	}
	
	
	/**
	 * Getter for token value.
	 * @return Current token.
	 */
	public Element getToken() {
		return this.token;
	}
	
	/**
	 * Extracts next token from text and then changes value of lexers
	 * current token to that value.
	 */
	public void extractNextToken() {
		if (token != null && token.getClass() == Element.class) {
			throw new SmartScriptLexerException("You have reached end of text");
		}

		if (currentIndex >= data.length) {
			// EOF
			throw new SmartScriptLexerException("End of text");
		}

		if (this.currentState == LexerState.TEXT) {
			if (data[currentIndex] == '\\') {
				String str = "";
				str += data[currentIndex++];
				System.out.println("tu sam");
				if (currentIndex >= data.length)
					throw new SmartScriptLexerException("Escape sign is  at the end.");

				if (data[currentIndex] == '\\' || data[currentIndex] == '{') {
					
					str += data[currentIndex++];
					while (currentIndex < data.length
							&& !(data[currentIndex] == '{' && data[currentIndex + 1] == '$')) {
						if (data[currentIndex++] == '\\') {
							if (data[currentIndex] == '\\' || data[currentIndex] == '{') {
								str += data[currentIndex];
								continue;
							}
						}
						str += data[currentIndex++];
					}
					token = new ElementString(str);
					return;
				}
				throw new SmartScriptLexerException("Invalid input.");
			}

			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				this.setState(LexerState.TAG);
				currentIndex += 2;
				this.extractNextToken();
				return;
			}
			
			String str = "";
			while (currentIndex < data.length && !(data[currentIndex] == '{' && data[currentIndex+1] == '$')) {
				if (data[currentIndex] == '\\') {
					str += data[currentIndex++];
					if (data[currentIndex] == '\\' || data[currentIndex] == '{') {
						str += data[currentIndex++];
						continue;
					}
					throw new SmartScriptLexerException("Invalid usage of escape operator.");
				}
				str += data[currentIndex++];
			}
			token = new ElementString(str);
			return;
			
			
			
			// State = TAG
		} else {
			while (data[currentIndex] == ' ') {
				currentIndex++;
			}
			if (data[currentIndex] == '=') {
				token = new ElementString("ECHO");
				currentIndex++;
				return;
			} else if (data[currentIndex] == 'F' && data[currentIndex+1] == 'O' && data[currentIndex+2] == 'R') {
				token = new ElementString("FOR");
				currentIndex += 3;
				return;
			} else if (data[currentIndex] == 'E' && data[currentIndex+1] == 'N' && data[currentIndex+2] == 'D') {
				token = new ElementString("END");
				currentIndex += 3;
				return;
			}

			if (data[currentIndex] == '-') {
				String str = "-";
				boolean isDouble = false;
				currentIndex++;
				if (Character.isDigit(data[currentIndex])) {
					str += data[currentIndex++];

					while (currentIndex < data.length
							&& (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
						if (data[currentIndex] == '.')
							isDouble = true;
						str += data[currentIndex++];
					}
					if (isDouble) {
						token = new ElementConstantDouble(Double.parseDouble(str));
					} else {
						token = new ElementConstantInteger(Integer.parseInt(str));
					}
				} else {
					token = new ElementOperator(str);
				}
				return;
			} 
			
			if (Character.isDigit(data[currentIndex])) {
				String str = "";
				boolean isDouble = false;
				str += data[currentIndex++];

				while (currentIndex < data.length
						&& (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
					if (data[currentIndex] == '.')
						isDouble = true;
					str += data[currentIndex++];
				}
				if (isDouble) {
					token = new ElementConstantDouble(Double.parseDouble(str));
				} else {
					token = new ElementConstantInteger(Integer.parseInt(str));
				}			
				return;
			}
			
			if (data[currentIndex] == '@') {
				String str = "";
				currentIndex++;
				while (currentIndex < data.length && data[currentIndex] != ' ') {
					str += data[currentIndex++];
				}
				token = new ElementFunction(str);
				return;
			}
			
			if (data[currentIndex] == '"') {
				String str = "";
				currentIndex++;
				while (currentIndex < data.length && data[currentIndex] != '"') {
					if (data[currentIndex] == '\\') {
						str += data[currentIndex++];
						if (data[currentIndex] == '\\' || data[currentIndex] == '"') {
							str += data[currentIndex++];
						} else {
							if(data[currentIndex] == 'n') {
								str += "n";
								currentIndex++;
								continue;
							}
							throw new SmartScriptLexerException("Invalid use of escape operator.");
						}
					}
					str += data[currentIndex++];
				}
				token = new ElementString(str);
				currentIndex++;
				return;
			}
			
			if ("+-*/^".indexOf(data[currentIndex]) != -1) {
				String str = "";
				str += data[currentIndex++];
				token = new ElementOperator(str);
				return;
			}
			
			if (data[currentIndex] == '$' && data[currentIndex+1] == '}') {
				token = new ElementString("ENDTAG");
				currentIndex += 2;
				this.setState(LexerState.TEXT);
				return;
			}
			
			String str = "";
			while (currentIndex < data.length && ("+-*/^$".indexOf(data[currentIndex]) == -1) && (data[currentIndex] != '@') && (data[currentIndex] != '"') && (data[currentIndex] != ' ')) {
				str += data[currentIndex++];
			}
			token = new ElementVariable(str);
			return;
			
		}

	}

}
