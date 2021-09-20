package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that stores operator string.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementString extends Element {

	private String value;
	
	/**
	 * Constructor with one argument.
	 * @param value - value of the string
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Provides text value of the ElementString.
	 * @return String form of the object ElementString.
	 */
	@Override
	public String asText() {
		return this.value;
	}
	
}