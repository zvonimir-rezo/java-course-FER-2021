package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the copy command which copies file content to another file or to another directory.
 * @author Zvonimir Petar Rezo
 *
 */
public class CopyCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	public CopyCommand() {
		commandName = "copy";
		commandDescription.add("Expects two arguments: source file name and destination file name.");
		commandDescription.add("If destination file exists, asks user if he is allowed to overwrite it.");
		commandDescription.add("Works only with files. If second argument is a directory, the file is copied into that directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		String[] splittedArgs = arguments.split(" ");
		File f1 = new File(splittedArgs[0]);
		File f2 = new File(splittedArgs[1]);
		if (f1.isDirectory()) {
			System.err.println("First argument can not be a directory.");
			return ShellStatus.CONTINUE;
		}
		if (f2.isDirectory()) {
			f2 = new File(splittedArgs[1] + "\\" +  f1.getName());
			
		}
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(f1);
			os = new FileOutputStream(f2);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			is.close();
			os.close();
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
