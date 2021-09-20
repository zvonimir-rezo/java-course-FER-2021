package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the cat command which opens the given file and
 * writes its content to console.
 * @author Zvonimir Petar Rezo
 *
 */
public class CatCommand implements ShellCommand {

	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public CatCommand() {
		commandName = "cat";
		commandDescription.add("First argument is path to some file and is mandatory.");
		commandDescription.add("Second argument is charset name that should be used to interpret chars from bytes.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		String[] splittedArgs = arguments.split(" ");

		File file = new File(splittedArgs[0]);
		BufferedReader br;
		try {
			if (splittedArgs.length == 1) {
				br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), Charset.defaultCharset()));
			} else {
				br = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), Charset.availableCharsets().get(splittedArgs[1])));
			}
			
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		} catch (IOException ex) {
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
