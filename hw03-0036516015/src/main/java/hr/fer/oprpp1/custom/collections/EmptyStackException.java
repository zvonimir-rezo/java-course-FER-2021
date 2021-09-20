package hr.fer.oprpp1.custom.collections;
/**
 * Implementation of EmptyStackError
 * @author Zvonimir Petar Rezo
 *
 */
public class EmptyStackException extends RuntimeException {
	public EmptyStackException(String errMessage) {
		super(errMessage);
	}
}
