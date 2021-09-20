package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of comparison operators such as <, >, =, !=, LIKE etc.
 * @author Zvonimir Petar Rezo
 *
 */
public class ComparisonOperators {
	String str1;
	String str2;
	
	
	public static final IComparisonOperator LESS = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (value1.compareTo(value2) < 0) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator LESS_OR_EQUALS = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (value1.compareTo(value2) <= 0) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator GREATER = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (value1.compareTo(value2) > 0) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator GREATER_OR_EQUALS = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (value1.compareTo(value2) >= 0) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator EQUALS = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (value1.equals(value2)) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator NOT_EQUALS = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			if (!value1.equals(value2)) {
				return true;
			}
			return false;
		}
	};
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		public boolean satisfied(String value1, String value2) {
			String firstPart = value2.substring(0, value2.indexOf('*'));
			if (value2.indexOf('*') == value2.length() - 1) {
				return value1.startsWith(firstPart);
			}
			String secondPart = value2.substring(value2.indexOf('*') + 1, value2.length());

			return (value1.startsWith(firstPart) && value1.endsWith(secondPart)
					&& value1.length() >= (firstPart.length() + secondPart.length()));
		}
	};
	
	/**
	 * Constructor for a comparison operator, takes in
	 * two arguments.
	 * @param str1 - first string in a comparison
	 * @param str2 - second string in a comparison
	 */
	public ComparisonOperators(String str1, String str2) {
		this.str1 = str1;
		this.str2 = str2;
	}

}
