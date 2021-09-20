package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the ls command which writes a directory listing of given directory.
 * @author Zvonimir Petar Rezo
 *
 */
public class LsCommand implements ShellCommand {

	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	public LsCommand() {
		commandName = "ls";
		commandDescription.add("Takes a single argument-directory.");
		commandDescription.add("Writes a directory listing.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		File f = new File(arguments);
		String[] pathnames = f.list();
		for (String pathname : pathnames) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path path = Paths.get(arguments + "/" + pathname);
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			env.write(Files.isDirectory(path) ? "d" : "-");
			env.write(Files.isReadable(path) ? "r" : "-");
			env.write(Files.isWritable(path) ? "w" : "-");
			env.write(Files.isExecutable(path) ? "x " : "- ");
			try {
				env.write(String.valueOf(Files.size(path)) + " ");
			} catch (ShellIOException e) {
				System.err.println("Cannot write to the file. Something went wrong.");
			} catch (IOException e) {
				System.err.println("Invalid path.");
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			env.write(formattedDateTime + " ");
			env.write(pathname + "\n");
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
