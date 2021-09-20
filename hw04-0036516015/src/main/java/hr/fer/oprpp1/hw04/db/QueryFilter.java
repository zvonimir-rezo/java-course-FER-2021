package hr.fer.oprpp1.hw04.db;
import java.util.List;

/**
 * Filter class used to filter student records.
 * Implements IFilter interface.
 * @author Zvonimir Petar Rezo
 *
 */
public class QueryFilter implements IFilter{
	List<ConditionalExpression> conditionalExps;
	
	/**
	 * Constructor which takes argument of a list
	 * of conditional expressions.
	 * @param conditionalExps - list of conditional expressions that need to be satisfied
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExps) {
		this.conditionalExps = conditionalExps;
	}
	
	/**
	 * Checks if the student record should be accepted by
	 * checking all conditional expressions.
	 * @param record - StudentRecord object to check conditional expressions on
	 * @return True if the record is accepted, false otherwise.
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (int i = 0; i < conditionalExps.size(); i++) {
			ConditionalExpression exp = conditionalExps.get(i);
			if (!exp.getcomparisonOperator().satisfied(exp.getfieldGetter().get(record), exp.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}	
}
