package hr.fer.zemris.lsystems.impl;
/**
 * User of this interface has to implement methods to get and insert objects to and from the list.
 * @author Zvonimir Petar Rezo
 *
 */
public interface List<T> extends Collection<T> {
	
	T get(int index);
	
	void insert(T value, int position);
	
	int indexOf(T value);
	
	void remove(int index);
	
}
