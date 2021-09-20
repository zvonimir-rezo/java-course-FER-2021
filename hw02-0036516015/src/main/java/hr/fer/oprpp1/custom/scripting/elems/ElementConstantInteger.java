package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that stores integer constant element.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	/**
	 * Constructor with one argument.
	 * @param value - integer value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Provides text value of the ElementConstantInteger.
	 * @return String form of the object.
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
	
}
