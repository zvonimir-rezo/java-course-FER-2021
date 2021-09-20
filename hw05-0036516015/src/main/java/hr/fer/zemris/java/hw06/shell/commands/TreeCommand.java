package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the tree command which prints a directory tree.
 * @author Zvonimir Petar Rezo
 *
 */
public class TreeCommand implements ShellCommand {

	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	public TreeCommand() {
		commandName = "tree";
		commandDescription.add("Expects a single argument of directory name.");
		commandDescription.add("Prints a tree for that directory");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		File f = new File(arguments);
		if (!f.isDirectory()) {
			System.err.println("Invalid path.");
			return ShellStatus.CONTINUE;
		}
		int indent = 0;
		StringBuilder sb = new StringBuilder();
		env.write(printDirectoryTree(f, indent, sb));
		return ShellStatus.CONTINUE;
	}
	
	private static String printDirectoryTree(File f, int indent, StringBuilder sb) {
		sb.append(indentString(indent));
		sb.append(f.getName());
		sb.append("\n");
		for (File file : f.listFiles()) {
	        if (file.isDirectory()) {
	            printDirectoryTree(file, indent + 2, sb);
	        } else {
	        	sb.append(indentString(indent + 2));
	            sb.append(file.getName());
	            sb.append("\n");
	        }
	    }
		return sb.toString();
	}
	
	private static String indentString(int indent) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(" ");
		}
		return sb.toString();
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
