package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Class that stores operator element.
 * @author Zvonimir Petar Rezo
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * Constructor with one argument.
	 * @param symbol - symbol of the operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Provides text value of the ElementOperator.
	 * @return String form of the object ElementOperator.
	 */
	@Override
	public String asText() {
		return String.valueOf(this.symbol);
	}
	
}