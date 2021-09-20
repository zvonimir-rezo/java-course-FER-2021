package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of getters of student record field values.
 * @author Zvonimir Petar Rezo
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = new IFieldValueGetter() {
		@Override
		public String get(StudentRecord record) {
			return record.getName();
		}		
	};
	
	public static final IFieldValueGetter LAST_NAME = new IFieldValueGetter() {
		@Override
		public String get(StudentRecord record) {
			return record.getSurname();
		}		
	};
	
	public static final IFieldValueGetter JMBAG = new IFieldValueGetter() {
		@Override
		public String get(StudentRecord record) {
			return record.getJmbag();
		}		
	};
	
}
