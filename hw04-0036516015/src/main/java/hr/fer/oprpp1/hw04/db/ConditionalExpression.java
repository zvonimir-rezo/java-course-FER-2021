package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of a conditional expression.
 * @author Zvonimir Petar Rezo
 *
 */
public class ConditionalExpression {

	private IFieldValueGetter fieldGetter;
	private IComparisonOperator comparisonOperator;
	private String stringLiteral;
	
	/**
	 * Empty constructor
	 */
	public ConditionalExpression() {
		
	}
	
	/**
	 * Constructor with three arguments to make a conditional expression.
	 * @param fieldGetter - object which implements IFieldValueGetter interface, provides information
	 * about what field value to get from student record
	 * @param stringLiteral - String object to compare to
	 * @param comparisonOperator - comparator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getfieldGetter() {
		return fieldGetter;
	}

	public void setfieldGetter(IFieldValueGetter fieldGetter) {
		this.fieldGetter = fieldGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public void setStringLiteral(String stringLiteral) {
		this.stringLiteral = stringLiteral;
	}

	public IComparisonOperator getcomparisonOperator() {
		return comparisonOperator;
	}

	public void setcomparisonOperator(IComparisonOperator comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}
	
	
	
}
