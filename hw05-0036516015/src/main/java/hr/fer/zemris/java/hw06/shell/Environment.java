package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface representing an environment through which the commands
 * communicate with the shell.
 * @author Zvonimir Petar Rezo
 *
 */
public interface Environment {
	/**
	 * Reads a line from shell.
	 * @return Line that was read.
	 * @throws ShellIOException if something goes wrong while reading the line
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes text to shell.
	 * @param text - string that needs to be written to shell
	 * @throws ShellIOException if something goes wrong while writing
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes a line to shell.
	 * @param text - string that needs to be written to shell
	 * @throws ShellIOException - if something goes wrong while writing
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * @return map of all commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * @return Current multiline symbol of the shell.
	 */
	Character getMultilineSymbol();

	/**
	 * Changes current multiline symbol to the value given in arguments.
	 * @param symbol - new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * @return Current prompt symbol of the shell.
	 */
	Character getPromptSymbol();

	/**
	 * Changes current prompt symbol to the value given in arguments.
	 * @param symbol - new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * @return Current morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Changes current morelines symbol to the value given in arguments.
	 * @param symbol - new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);


}
