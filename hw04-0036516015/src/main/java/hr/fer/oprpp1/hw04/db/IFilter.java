package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of filter interface. Filters student records.
 * @author Zvonimir Petar Rezo
 *
 */
public interface IFilter {
	
	/**
	 * Checks if the given record satisfies a condition and then filters it accordingly.
	 * @param record - record to check
	 * @return True if the filter accepts the record, false otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
