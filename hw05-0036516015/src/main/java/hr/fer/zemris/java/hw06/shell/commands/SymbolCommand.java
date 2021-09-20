package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the symbol command which is used to manipulate shell symbols.
 * @author Zvonimir Petar Rezo
 *
 */
public class SymbolCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription = new ArrayList<>();
	
	public SymbolCommand() {
		commandName = "symbol";
		commandDescription.add("Provides current symbol for wanted attribute.");
		commandDescription.add("Can also be used to change current symbol for wanted attribute.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] splittedArgs = arguments.split(" ");
		if (splittedArgs.length == 1) {
			if (splittedArgs[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if (splittedArgs[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else if (splittedArgs[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			}
			
		} else {
			if (splittedArgs[0].equals("PROMPT")) {
				char c  = env.getPromptSymbol();
				env.setPromptSymbol((Character.valueOf(splittedArgs[1].charAt(0))));
				env.writeln("Symbol for PROMPT changed from '" + c + "' to '" + env.getPromptSymbol() + "'");
			} else if (splittedArgs[0].equals("MORELINES")) {
				char c  = env.getMorelinesSymbol();
				env.setMorelinesSymbol((Character.valueOf(splittedArgs[1].charAt(0))));
				env.writeln("Symbol for MORELINES changed from '" + c + "' to '" + env.getMorelinesSymbol() + "'");
			} else if (splittedArgs[0].equals("MULTILINE")) {
				char c  = env.getMultilineSymbol();
				env.setMultilineSymbol((Character.valueOf(splittedArgs[1].charAt(0))));
				env.writeln("Symbol for MULTILINE changed from '" + c + "' to '" + env.getMultilineSymbol() + "'");
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
