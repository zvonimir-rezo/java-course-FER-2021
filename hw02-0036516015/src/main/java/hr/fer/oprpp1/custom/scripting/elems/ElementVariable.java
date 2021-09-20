package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that stores variable element.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	/**
	 * Constructor with one argument.
	 * @param name - name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Provides text value of the ElementString.
	 * @return String form of the object ElementString.
	 */
	@Override
	public String asText() {
		return this.name;
	}
	
}
