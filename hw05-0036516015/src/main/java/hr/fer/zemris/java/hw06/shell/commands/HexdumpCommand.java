package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Implementation of the hexdump command which produces hex-output of the given file.
 * @author Zvonimir Petar Rezo
 *
 */
public class HexdumpCommand implements ShellCommand {
	
	private String commandName;
	private List<String> commandDescription = new ArrayList<>();

	public HexdumpCommand() {
		commandName = "hexdump";
		commandDescription.add("Expects a single argument: file name");
		commandDescription.add("Produces hex output.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = ArgumentFormatter.format(arguments);
		File f1 = new File(arguments);
		if (f1.isDirectory()) {
			System.err.println("First argument can not be a directory.");
			return ShellStatus.CONTINUE;
		}

		InputStream is = null;
		try {
			int br = 0;
			int last = 0;
			byte[] buffer = new byte[16];
			for (byte b : Files.readAllBytes(Paths.get(arguments))) {
				buffer[br%16] = b;
				br++;
				if (br % 16 == 0) {
					System.out.printf("%08x: ", br-16);
					last = br-16;
					String s = Util.bytetohex(buffer);
					char[] chars = s.toCharArray();
					for (int i = 0; i < chars.length; i++) {
						System.out.print(chars[i]);
						if ((i+1) % 16 == 0) {
							System.out.print("|");
							continue;
						}
						if ((i+1) % 2 == 0) {
							System.out.print(" ");
						}
					}
					for (byte c : buffer) {
						if (c < 32 || c > 127) {
							System.out.print('.');
						} else {
							System.out.print((char)c);
						}
						
					}
					System.out.print("\n");
					for (int i = 0; i < buffer.length; i++) {
						buffer[i] = 0;
					}
				}
				
			}
			if (buffer[0] != 0) {
				System.out.printf("%08x: ", last+16);
				String s = Util.bytetohex(buffer);
				char[] chars = s.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (chars[i] == '0') {
						System.out.print(" ");
					} else {
						System.out.print(chars[i]);
					}
					if ((i+1) % 16 == 0) {
						System.out.print("|");
						continue;
					}
					if ((i+1) % 2 == 0) {
						System.out.print(" ");
					}
				}
				for (byte c : buffer) {
					if (c == 0) {
						break;
					} else if (c < 32 || c > 127) {
						System.out.print('.');
					} else {
						System.out.print((char)c);
					}	
				}
				System.out.print("\n");
			}
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
