package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the charsets command which lists names of supported charsets.
 * @author Zvonimir Petar Rezo
 *
 */
public class CharsetsCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription;
	
	public CharsetsCommand() {
		commandName = "charsets";
		commandDescription = new ArrayList<String>();
		commandDescription.add("Lists names of supported charsets.");
		commandDescription.add("A single charset name is written per line.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, Charset> charsets = Charset.availableCharsets();
		for (Charset c : charsets.values()) {
			System.out.println(c.displayName());
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
