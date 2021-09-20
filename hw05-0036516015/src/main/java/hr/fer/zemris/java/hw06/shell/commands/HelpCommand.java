package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the help command which provides information about other commands.
 * @author Zvonimir Petar Rezo
 *
 */
public class HelpCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription = new ArrayList<>();
	
	public HelpCommand() {
		commandName = "help";
		commandDescription.add("Provides information about other commands.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		if (arguments.equals("help")) {
			for (String command : env.commands().keySet()) {
				env.writeln(command);
			}
		} else {
			if (env.commands().keySet().contains(arguments)) {
				env.writeln(env.commands().get(arguments).getCommandName());
				for (String s : env.commands().get(arguments).getCommandDescription()) {
					env.writeln(s);
				}
			} else {
				System.err.println("No such command.");
			}
		}
		return ShellStatus.CONTINUE;
		
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
