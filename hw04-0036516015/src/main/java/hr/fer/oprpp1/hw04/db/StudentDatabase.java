package hr.fer.oprpp1.hw04.db;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of a student database. Basically stores StudentRecord objects.
 * @author Zvonimir Petar Rezo
 *
 */
public class StudentDatabase {

	List<StudentRecord> studentRecords = new ArrayList<>();
	Map<String, StudentRecord> studentRecordsMap = new HashMap<>();
	
	/**
	 * Private class used to format StudentRecord objects into strings.
	 * @author Zvonimir Petar Rezo
	 *
	 */
	private static class RecordFormatter {
		public static String formatPrint(List<StudentRecord> st) {
			String endStr = "+============+";
			int longestSurname = 0;
			int longestName = 0;
			for (StudentRecord sr : st) {
				if (sr.getSurname().length() > longestSurname) {
					longestSurname = sr.getSurname().length();
				}
				if (sr.getName().length() > longestName) {
					longestName = sr.getName().length();
				}
			}
			for (int i = 0; i < longestSurname+2; i++) {
				endStr += "=";
			}
			endStr += "+";
			for (int i = 0; i < longestName+2; i++) {
				endStr += "=";
			}
			endStr += "+===+\n";
			for (int i = 0; i < st.size(); i++) {
				endStr += ("| " + st.get(i).getJmbag() + " | " + String.format("%-" + longestSurname + "s", st.get(i).getSurname()) + " | " + String.format("%-" + longestName + "s", st.get(i).getName()) + " | " + st.get(i).getGrade() + " |\n");	
			}
			endStr += "+============+";
			for (int i = 0; i < longestSurname+2; i++) {
				endStr += "=";
			}
			endStr += "+";
			for (int i = 0; i < longestName+2; i++) {
				endStr += "=";
			}
			endStr += "+===+";
			return endStr;

		}
	}
	
	/**
	 * Constructor which takes in arguments a list of lines. Every line
	 * corresponds to a student record.
	 * @param lines - list of all lines
	 */
	public StudentDatabase(List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String[] splitted = lines.get(i).split("\\s+");
			StudentRecord student;
			if (Integer.parseInt(splitted[splitted.length-1]) < 1 || Integer.parseInt(splitted[splitted.length-1]) > 5 || studentRecordsMap.containsKey(splitted[0])) {
				throw new IllegalArgumentException();
			}
			if (splitted.length == 5) {
				student = new StudentRecord(splitted[0], splitted[1] + splitted[2], splitted[3], Integer.parseInt(splitted[4]));
			} else {
				student = new StudentRecord(splitted[0], splitted[1], splitted[2], Integer.parseInt(splitted[3]));
			}
			
			studentRecords.add(student);
			studentRecordsMap.put(splitted[0], student);
		}
	}
	
	/**
	 * Gets a student record by jmbag in O(1) time.
	 * @param jmbag
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentRecordsMap.get(jmbag);
	}
	
	/**
	 * Method which filters list of student records by the rules of filter
	 * given in arguments.
	 * @param filter - Filter by whose rules the method should filter
	 * @return List of filtered student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		ArrayList<StudentRecord> recordsTmp = (ArrayList<StudentRecord>) studentRecords.stream()
					.filter(st -> filter.accepts(st))
					.collect(Collectors.toList());
		return recordsTmp;
		
	}
	
	/**
	 * Queries the given text.
	 * @param text - text that needs to be processed
	 */
	public void query(String text) {
		QueryParser parser = new QueryParser(text.replace("query ", ""));
		List<StudentRecord> filteredStudents = new ArrayList<>();
		if (parser.isDirectQuery()) {
			StudentRecord studRecord = forJMBAG(parser.getQueriedJMBAG());
			System.out.println("Using index for record retrieval.");
			filteredStudents.add(studRecord);
		} else {
			List<ConditionalExpression> condExp = parser.getQuery();
			filteredStudents = filter(new QueryFilter(condExp));
		}
		if (filteredStudents.size() > 0) {
			System.out.println(RecordFormatter.formatPrint(filteredStudents));
		}
		System.out.println("Records selected: " + filteredStudents.size());
//		stringsOfStudents.forEach(System.out::println);
	}

	
}
