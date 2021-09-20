package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a parser for given queries.
 * @author Zvonimir Petar Rezo
 *
 */
public class QueryParser {

	String queryString;
	QueryLexer lexer;
	List<Token> tokenList;

	
	/**
	 * Constructor which takes a string argument.
	 * @param queryString - string that needs to be parsed by this parser
	 */
	public QueryParser(String queryString) {
		this.queryString = queryString;
		this.lexer = new QueryLexer(queryString);
	}

	/**
	 * Checks if the query consists only of jmbag="x", where x is arbitrary number of digits.
	 * @return True if query is direct, false otherwise.
	 */
	public boolean isDirectQuery() {
		tokenList = new ArrayList<>();
		while (true) {
			Token token = lexer.nextToken();
			if (token.getType() == TokenType.EOL) {
				break;
			}
			tokenList.add(token);
		}
		lexer.setCurrentIndex(0);
		if (tokenList.get(0).getValue().equals("jmbag") && tokenList.get(1).getValue().equals("=")
				&& tokenList.get(2).getType() == TokenType.STRING && tokenList.size() == 3) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the queried jmbag if query is direct.
	 * @return queried jmbag
	 * @throws IllegalStateException if the query is not direct
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return tokenList.get(2).getValue();
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Gets all contidional expressions from the query and stores them into a list.
	 * @return List of conditional expressions.
	 */
	public List<ConditionalExpression> getQuery() {
		int expressionIndex = 0;
		List<ConditionalExpression> condExps = new ArrayList<>();
		ConditionalExpression tmpConditionalExpression = new ConditionalExpression();
		while (true) {
			if (expressionIndex == 3) {
				if (tmpConditionalExpression.getfieldGetter() != null) {
					condExps.add(tmpConditionalExpression);
				}
				tmpConditionalExpression = new ConditionalExpression();
				expressionIndex = 0;
			}
			Token token = lexer.nextToken();
			if (token.getType() == TokenType.EOL) {
				break;
			}
			if (token.getType() == TokenType.AND) {
				continue;
			}
			if (token.getType() == TokenType.WORD) {
				if (token.getValue().equals("jmbag")) {
					tmpConditionalExpression.setfieldGetter(FieldValueGetters.JMBAG);
				} else if (token.getValue().equals("firstName")) {
					tmpConditionalExpression.setfieldGetter(FieldValueGetters.FIRST_NAME);
				} else {
					tmpConditionalExpression.setfieldGetter(FieldValueGetters.LAST_NAME);
				}
				expressionIndex = 1;
			}

			if (token.getType() == TokenType.OPERATOR) {
				String value = token.getValue();
				if (value.equals("<")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.LESS);
				} else if (value.equals("<=")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.LESS_OR_EQUALS);
				} else if (value.equals(">")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.GREATER);
				} else if (value.equals(">=")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.GREATER_OR_EQUALS);
				} else if (value.equals("=")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.EQUALS);
				} else if (value.equals("!=")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.NOT_EQUALS);
				} else if (value.equals("LIKE")) {
					tmpConditionalExpression.setcomparisonOperator(ComparisonOperators.LIKE);
				}
				expressionIndex = 2;
			}

			if (token.getType() == TokenType.STRING) {
				tmpConditionalExpression.setStringLiteral(token.getValue());
				expressionIndex = 3;
			}

		}
		return condExps;
	}

}
