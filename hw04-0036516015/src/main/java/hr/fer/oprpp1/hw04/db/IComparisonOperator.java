package hr.fer.oprpp1.hw04.db;

/**
 * Interface that stands for a comparison operator.
 * @author Zvonimir Petar Rezo
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Checks if the comparison condition is satisfied.
	 * @param value1 - first string in comparison
	 * @param value2 - second string in comparison
	 * @return True if the condition is satisfied, false otherwise.
	 */
	public boolean satisfied(String value1, String value2);
	
}
