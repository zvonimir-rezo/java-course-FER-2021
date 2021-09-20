package hr.fer.zemris.lsystems.impl;
/**
 * Interface with only test method. Tests an object by some feature.
 * @author Zvonimir Petar Rezo
 *
 */
public interface Tester<T> {

	boolean test(T obj);
	
}
