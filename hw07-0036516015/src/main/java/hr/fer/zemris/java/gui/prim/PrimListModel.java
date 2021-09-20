package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of a list model for prime number display.
 * @author Zvonimir Petar Rezo
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private List<Integer> primes;
	private List<ListDataListener> listeners;
	private int lastReturned;
	
	/**
	 * Default constructor. Initializes the lists and adds 1 to list of prime numbers.
	 */
	public PrimListModel() {
		primes = new ArrayList<Integer>();
		listeners = new ArrayList<ListDataListener>();
		lastReturned = 1;
		primes.add(1);
	}
	
	/**
	 * Method called upon clicking the next button. Adds next prime number to the list.
	 */
	public void next() {
		while (true) {
			lastReturned++;
			if (checkPrime(lastReturned)) {
				primes.add(lastReturned);
				break;
			}
		}
		notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize()));
		
	}
	
	/**
	 * Checks if a number is prime.
	 * @param number - number which is checked
	 * @return True if the number is prime, false otherwise.
	 */
	private boolean checkPrime(int number) {
		for (int i = 2; i <= number / 2; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * Getter for current size of the list.
	 * @return Size of the list of prime numbers.
	 */
	@Override
	public int getSize() {
		return primes.size();
	}

	/**
	 * Returns element at the given index.
	 * @param index - given index
	 * @return Integer value of the prime number at given index.
	 */
	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	/**
	 * Adds a listener to list of listeners.
	 * @param l - listener that needs to be added
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/**
	 * Removes the listener from the list of listeners.
	 * @param l - lsitener that needs to be removed
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	
	/**
	 * Notifies listeners about a change.
	 * @param e - event that occurred
	 */
	private void notifyListeners(ListDataEvent e) {
		for (ListDataListener l: listeners) {
			l.intervalAdded(e);
		}
	}
	
}
