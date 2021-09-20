package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Abstract class. Parent of more specific Element classes like ElementString, ElementOperator etc.
 * @author Zvonimir Petar Rezo
 *
 */
public abstract class Element {

	/**
	 * Gets the text value of an element.
	 * @return Element in a text form.
	 */
	public String asText() {
		return "";
	}
	
	/**
	 * Checks if the current element is equal to another element.
	 * @param other - other Element object
	 * @return True if the elements are equal, false otherwise.
	 */
	public boolean equalsElement(Element other) {
		if (this.asText().equals(other.asText())) {
			return true;
		}
		return false;
	}
	
}
