package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the mkdir command which creates a directory.
 * @author Zvonimir Petar Rezo
 *
 */
public class MkdirCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	public MkdirCommand() {
		commandName = "mkdir";
		commandDescription.add("Expects a single argument: directory name");
		commandDescription.add("Creates appropriate directory structure.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		Path path = Paths.get(arguments);
		try {
			Files.createDirectory(path);
		} catch (IOException e) {
			System.err.println("Invalid path.");
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
