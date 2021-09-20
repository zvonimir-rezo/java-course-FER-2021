package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

class SmartScriptParserTest {
	
	
	@Test
	void testNumberOfChildren() {
		SmartScriptParser parser = new SmartScriptParser("text {$= tag $} more text");
		assertEquals(3, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	void testChildrenOfChildren() {
		SmartScriptParser parser = new SmartScriptParser("text {$FOR i 1 10 1 $}\r\n some random text\r\n {$END$}");
		assertEquals(1, parser.getDocumentNode().getChild(1).numberOfChildren());
	}
	
	@Test
	void testThrowsExceptionOnInvalidEscape() {
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser("Text goes \\n on");
		});
	}

	@Test
	void testDocumentNodeForSimpleText() {
		SmartScriptParser parser = new SmartScriptParser("text");
		assertEquals(DocumentNode.class, parser.getDocumentNode().getClass());
	}
	
	@Test
	void testNumberOfForLoopArguments() {	
		assertThrows(SmartScriptParserException.class, () -> {
			SmartScriptParser parser = new SmartScriptParser("text {$FOR i 1 10 100 1 $}\r\n some random text\r\n {$END$}");
		});
	}

}
