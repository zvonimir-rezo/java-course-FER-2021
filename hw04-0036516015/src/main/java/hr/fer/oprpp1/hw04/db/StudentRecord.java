package hr.fer.oprpp1.hw04.db;

/**
 * Implementation of a student record.
 * @author Zvonimir Petar Rezo
 *
 */
public class StudentRecord {
	private String jmbag;
	private String surname;
	private String name;
	private int grade;
	
	/**
	 * Constructor which takes four arguments.
	 * @param jmbag - jmbag of the student
	 * @param surname - surname of the student
	 * @param name - name of the student
	 * @param grade - grade the student got
	 */
	public StudentRecord(String jmbag, String surname, String name, int grade) {
		this.jmbag = jmbag;
		this.surname = surname;
		this.name = name;
		this.grade = grade;
	}

	/**
	 * Generates hashcode from jmbag.
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Checks if current StudentRecord object equals another object by
	 * comparing jmbags.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	public String getJmbag() {
		return jmbag;
	}

	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}


	
}
