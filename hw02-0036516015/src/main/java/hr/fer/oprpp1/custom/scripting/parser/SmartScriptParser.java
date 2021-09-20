package hr.fer.oprpp1.custom.scripting.parser;


import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

/**
 * Parser class used to parse a text by the rules explained in its methods.
 * @author Zvonimir Petar Rezo
 *
 */
public class SmartScriptParser {

	private SmartScriptLexer lexer;
	private ObjectStack stack;
	private DocumentNode node;
	private Node top;
	

	/**
	 * Parser constructor.
	 * @param body - text which needs to be parsed
	 */
	public SmartScriptParser(String body) {
		this.lexer = new SmartScriptLexer(body);
		this.stack = new ObjectStack(10);
		this.node = new DocumentNode();
		this.stack.push(node);
		this.top = node;
		this.parse();
	}

	/**
	 * Method used to parse the given text.
	 */
	public void parse() {
		try {
			while (true) {
				Element el = lexer.nextToken();
				ArrayIndexedCollection tagElements = new ArrayIndexedCollection();
				if (el.asText().equals("ECHO")) {
					el = lexer.nextToken();
					while (!el.asText().equals("ENDTAG")) {
						tagElements.add(el);
						el = lexer.nextToken();
					}
					Element[] tagElementsArray = new Element[tagElements.size()];

					for (int i = 0; i < tagElements.size(); i++) {
						if (tagElements.get(i).getClass().equals(ElementFunction.class)) {
							tagElementsArray[i] = (ElementFunction) tagElements.get(i);
						} else if (tagElements.get(i).getClass().equals(ElementString.class)) {
							tagElementsArray[i] = (ElementString) tagElements.get(i);
						} else {
							tagElementsArray[i] = (Element) tagElements.get(i);
						}
						
					}
					
					EchoNode node = new EchoNode(tagElementsArray);
					top.addChildNode(node);

				} else if (el.asText().equals("FOR")) {
					el = lexer.nextToken();
					while (!el.asText().equals("ENDTAG")) {
						tagElements.add(el);
						el = lexer.nextToken();
					}
					if (tagElements.size() > 4 || tagElements.size() < 3) {
						throw new SmartScriptParserException("Invalid number of arguments in for loop.");
					}
					Element[] tagElementsArray = new Element[tagElements.size()];
					
					for (int i = 0; i < tagElements.size(); i++) {
						if (tagElements.get(i).getClass().equals(ElementFunction.class)) {
							tagElementsArray[i] = (ElementFunction) tagElements.get(i);
						} else if (tagElements.get(i).getClass().equals(ElementString.class)) {
							tagElementsArray[i] = (ElementString) tagElements.get(i);
						} else {
							tagElementsArray[i] = (Element) tagElements.get(i);
						}
					}
					
					ForLoopNode node;
					try {
						if (tagElementsArray.length == 4) {
							node = new ForLoopNode((ElementVariable) tagElementsArray[0], tagElementsArray[1],
									tagElementsArray[2], tagElementsArray[3]);
						} else {
							node = new ForLoopNode((ElementVariable) tagElementsArray[0], tagElementsArray[1],
									tagElementsArray[2]);
						}
					} catch (Exception ex) {
						throw new SmartScriptParserException("Invalid for loop.");
					}

					top.addChildNode(node);
					stack.push(node);
					top = node;

				} else if (el.asText().equals("END")) {
					if (lexer.nextToken().asText() == "ENDTAG") {
						stack.pop();
						top = (Node) stack.peek();
					} else {
						throw new SmartScriptParserException("Invalid end tag.");
					}

				} else {
					top.addChildNode(new TextNode(el.asText()));
				}
			}
		} catch (SmartScriptLexerException ex) {
			if (ex.getMessage().equals("End of text")) {
				return;
			}
			throw new SmartScriptParserException(ex.getMessage());
		}
	}

	/**
	 * Getter for document node. That is the first node of the document and all other nodes are children of this node.
	 * @return Document node which is the first node of the document.
	 */
	public DocumentNode getDocumentNode() {
		return this.node;
	}

}
