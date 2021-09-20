package hr.fer.zemris.java.hw06.shell.commands;

/**
 * Implementation of a class used to format arguments for commands to use.
 * @author Zvonimir Petar Rezo
 *
 */
public class ArgumentFormatter {

	public static String format(String str) {
		boolean inside = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (inside && str.charAt(i) == ' ')
				continue;
			if (str.charAt(i) == '"' && !inside) {
				inside = true;
				continue;
			}
			if (str.charAt(i) == '"' && inside) {
				inside = false;
				continue;
			}
			sb.append(str.charAt(i));
		}
		if (inside)
			throw new IllegalArgumentException("quotes not closed.");
		return sb.toString();
	}
	
}
