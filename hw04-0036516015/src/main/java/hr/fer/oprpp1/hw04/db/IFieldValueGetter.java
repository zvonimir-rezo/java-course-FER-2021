package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of getter for field values of student records.
 * @author Zvonimir Petar Rezo
 *
 */
public interface IFieldValueGetter {
	/**
	 * Getter for a certain field value of given student record.
	 * @param record - student whose field value this method should get
	 * @return Field value that was asked for.
	 */
	public String get(StudentRecord record);
	
}
