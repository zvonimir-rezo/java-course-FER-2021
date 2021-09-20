package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Class representing a text node.
 * Extends the Node class.
 * @author Zvonimir Petar Rezo
 *
 */
public class TextNode extends Node {

	private String text;
	
	/**
	 * Constructor which takes in one argument.
	 * @param text - text that TextNode contains
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Getter for text value of current TextNode.
	 * @return Text variable of current TextNode.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Converts current TextNode to string value.
	 * @return String value of current TextNode.
	 */
	@Override
	public String toString() {
		
		return this.getText();
	}
	
	/**
	 * Checks if current TextNode is equal to another object.
	 * @return True if two objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == null || other == null) {
			return false;
		}
		if (!(other instanceof TextNode)) {
			return false;
		}
		
		if (!(this.toString().equals(other.toString()))) {
			return false;
		}
		return true;
	}
}
