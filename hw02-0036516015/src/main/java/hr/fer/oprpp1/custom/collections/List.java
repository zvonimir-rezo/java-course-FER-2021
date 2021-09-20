package hr.fer.oprpp1.custom.collections;
/**
 * User of this interface has to implement methods to get and insert objects to and from the list.
 * @author Zvonimir Petar Rezo
 *
 */
public interface List extends Collection {
	
	Object get(int index);
	
	void insert(Object value, int position);
	
	int indexOf(Object value);
	
	void remove(int index);
	
}
