package hr.fer.oprpp1.custom.scripting.elems;
/**
 * Class that stores double constant element.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	/**
	 * Constructor  with one argument.
	 * @param value - value of the double constant
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Provides text value of the ElementConstantDouble.
	 * @return String form of the object.
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
	
}