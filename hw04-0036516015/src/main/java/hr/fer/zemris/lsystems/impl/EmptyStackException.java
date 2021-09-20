package hr.fer.zemris.lsystems.impl;
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
