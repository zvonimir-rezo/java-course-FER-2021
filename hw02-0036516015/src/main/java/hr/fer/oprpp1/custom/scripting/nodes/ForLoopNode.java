package hr.fer.oprpp1.custom.scripting.nodes;


import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Node class that represents a for loop.
 * The object of this class can have its own children.
 * @author Zvonimir Petar Rezo
 *
 */
public class ForLoopNode extends Node {

	private ElementVariable variable = null;
	private Element startExpression = null;
	private Element endExpression = null;
	private Element stepExpression = null;
	
	/**
	 * Constructor for for loop with four arguments.
	 * @param variable - variable of a for loop
	 * @param startExpression - starting value of a variable
	 * @param endExpression - ending value of a variable
	 * @param stepExpression - step at which variable moves toward endExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Constructor for for loop with three arguments. Default step is 1.
	 * @param variable - variable of a for loop
	 * @param startExpression - starting value of a variable
	 * @param endExpression - ending value of a variable
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
	}
	
	/**
	 * Converts current ForLoopNode to string value.
	 * @return String value of current for loop node.
	 */
	@Override
	public String toString() {
		String str = "";
		str += ("{$FOR " + variable.asText() + " ");
		if (startExpression.getClass().equals(ElementFunction.class)) {
			str += "@" + startExpression.asText() + " ";
		} else if (startExpression.getClass().equals(ElementString.class)) {
			str += "\"" + startExpression.asText() + "\"" + " ";
		} else {
			str += startExpression.asText() + " ";
		}
		
		if (endExpression.getClass().equals(ElementFunction.class)) {
			str += "@" + endExpression.asText() + " ";
		} else if (endExpression.getClass().equals(ElementString.class)) {
			str += "\"" + endExpression.asText() + "\"" + " ";
		} else {
			str += endExpression.asText() + " ";
		}
		
		if (stepExpression.getClass().equals(ElementFunction.class)) {
			str += "@" + stepExpression.asText() + " ";
		} else if (stepExpression.getClass().equals(ElementString.class)) {
			str += "\"" + stepExpression.asText() + "\"" + " ";
		} else {
			str += stepExpression.asText() + " ";
		}
		str += "$}";
		
		
//		+ startExpression.asText() + " " + endExpression.asText() + " "
//		+ stepExpression.asText() + " " + " $}\r\n");
		for (Object n : children.toArray()) {
			if (n != null) {
				str += n.toString();
			} else {
				break;
			}
			
		}
		//str += "\r\n";
		str += "{$END$}";
		return str;
	}
	
	/**
	 * Checks if current ForLoopNode is equal to another object.
	 * @return True if two objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object other) {
		if (this == null || other == null) {
			return false;
		}
		if (!(other instanceof ForLoopNode)) {
			return false;
		}
		
		if (this.numberOfChildren() != ((ForLoopNode) other).numberOfChildren()) {
			return false;
		}
		
		boolean rtn = true;
		for (int i = 0; i < this.numberOfChildren(); i++) {
			if (!(this.getChild(i).equals(((ForLoopNode) other).getChild(i)))) {
				rtn = false;
			}
		}
		return rtn;
			
	}
	
	
	
}
