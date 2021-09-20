package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;

/**
 * Node class which represents the echo tags and everything between then.
 * 
 * @author Zvonimir Petar Rezo
 *
 */
public class EchoNode extends Node {

	private Element[] elements;

	/**
	 * Constructor which takes one argument.
	 * 
	 * @param elements - array of elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Getter for the elements array.
	 * 
	 * @return Elements array of the current EchoNode
	 */
	public Element[] getElements() {
		return this.elements;
	}

	/**
	 * Converts current EchoNode to string value.
	 * 
	 * @return String value of current EchoNode.
	 */
	@Override
	public String toString() {
		String str = "";
		str += "{$= ";
		for (Element e : elements) {
			if (e.getClass().equals(ElementFunction.class)) {
				str += "@" + e.asText() + " ";
			} else if (e.getClass().equals(ElementString.class)) {
				str += "\"" + e.asText() + "\"" + " ";
			} else {
				str += e.asText() + " ";
			}

		}
		str += "$}";
		return str;
	}
	
	/**
	 * Checks if current EchoNode is equal to another object.
	 * @return True if two objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == null || other == null) {
			return false;
		}
		if (!(other instanceof EchoNode)) {
			return false;
		}
		Element[] el1 = this.getElements();
		Element[] el2 = ((EchoNode) other).getElements();
		for(int i = 0; i < el1.length; i++) {
			try {
				if (!(el1[i].equalsElement(el2[i]))) {
					return false;
				}
			}catch(NullPointerException ex) {
				return false;
			}
			
		}
		
		return true;
		
	}
}
