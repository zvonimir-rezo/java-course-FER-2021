package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the exit command which terminates the shell.
 * @author Zvonimir Petar Rezo
 *
 */
public class ExitCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription;
	
	public ExitCommand() {
		commandName = "exit";
		commandDescription = new ArrayList<String>();
		commandDescription.add("Terminates the shell.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
