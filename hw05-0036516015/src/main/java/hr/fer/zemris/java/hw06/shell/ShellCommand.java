package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface representing a shell command.
 * @author Zvonimir Petar Rezo
 *
 */
public interface ShellCommand {
	/**
	 * Executes the command.
	 * @param env - environment through which the command communicates with the shell
	 * @param arguments - arguments for the command to use
	 * @return Status the shell should be in after the command executes.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * @return Name of the command.
	 */
	String getCommandName();
	
	/**
	 * @return Description of the command.
	 */
	List<String> getCommandDescription();
}
