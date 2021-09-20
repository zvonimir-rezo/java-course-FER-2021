package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that stores function element.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * Constructor with one argument.
	 * @param name - name of the function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Provides text value of the ElementFunction.
	 * @return String form of the object ElementFunction.
	 */
	@Override
	public String asText() {
		return this.name;
	}
	
}