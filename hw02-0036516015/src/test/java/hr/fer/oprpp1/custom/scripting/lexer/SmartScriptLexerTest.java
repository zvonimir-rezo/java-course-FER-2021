package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SmartScriptLexerTest {

	@Test
	void simpleTextTest() {
		String testStr = "The most simple text ever.";
		SmartScriptLexer lexer = new SmartScriptLexer(testStr);
		assertEquals("The most simple text ever.", lexer.nextToken().asText());
	}
	
	@Test
	void simpleTagTest() {
		String testStr = "The most simple text with {$= tag $} ever.";
		SmartScriptLexer lexer = new SmartScriptLexer(testStr);
		assertEquals("The most simple text with ", lexer.nextToken().asText());
		assertEquals("ECHO", lexer.nextToken().asText());
	}
	
	@Test
	void simpleForTagTest() {
		String testStr = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}\r\n"
				+ " This is {$= i $}-th time this message is generated.\r\n"
				+ "{$END$}\r\n";
		SmartScriptLexer lexer = new SmartScriptLexer(testStr);
		lexer.nextToken();
		assertEquals("FOR", lexer.nextToken().asText());
	}
	
	@Test
	void escapeTest() {
		String testStr = "The most simple \\{ text ever.";
		SmartScriptLexer lexer = new SmartScriptLexer(testStr);
		assertEquals("The most simple \\{ text ever.", lexer.nextToken().asText());
	}
	
	@Test
	void escapeInsideTagTest() {
		String testStr = "Simple tag. {$ = \"testing \\\"this\\\" escape\"$}";
		SmartScriptLexer lexer = new SmartScriptLexer(testStr);
		assertEquals("Simple tag. ", lexer.nextToken().asText());
		assertEquals("ECHO", lexer.nextToken().asText());
		assertEquals("testing \\\"this\\\" escape", lexer.nextToken().asText());
	}

}
