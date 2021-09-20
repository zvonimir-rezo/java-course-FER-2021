package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

public class MyShell {
	
	private static char multilineSymbol = '|';
	private static char morelinesSymbol = '\\';
	private static char promptSymbol = '>';
	private static ShellStatus status = ShellStatus.CONTINUE;
	
	public static void main(String[] args) {
		
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put("symbol", new SymbolCommand());
		commands.put("cat", new CatCommand());
		commands.put("charsets", new CharsetsCommand());
		commands.put("copy", new CopyCommand());
		commands.put("hexdump", new HexdumpCommand());
		commands.put("ls", new LsCommand());
		commands.put("mkdir", new MkdirCommand());
		commands.put("tree", new TreeCommand());
		commands.put("help", new HelpCommand());
		commands.put("exit", new ExitCommand());
		
		Environment env = new Environment() {
			
			public String readLine() throws ShellIOException {
				Scanner sc = new Scanner(System.in);
				String line = sc.nextLine();
				sc.close();
				return line;
			}

			public void write(String text) throws ShellIOException {
				System.out.print(text);
				
			}

			public void writeln(String text) throws ShellIOException {
				System.out.println(text);
				
			}

			public SortedMap<String, ShellCommand> commands() {
				return commands;
			}

			public Character getMultilineSymbol() {
				return multilineSymbol;
			}

			public void setMultilineSymbol(Character symbol) {
				multilineSymbol = symbol;
				
			}

			public Character getPromptSymbol() {
				return promptSymbol;
			}

			public void setPromptSymbol(Character symbol) {
				promptSymbol = symbol;
				
			}

			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}

			public void setMorelinesSymbol(Character symbol) {
				morelinesSymbol = symbol;
				
			}
			
		};
		
		System.out.println("Welcome to MyShell v 1.0");
		
		Scanner sc = new Scanner(System.in);
		String line;
		while (status != ShellStatus.TERMINATE) {
			System.out.print(promptSymbol);
			List<String> lines = new ArrayList<>();
			line = sc.nextLine();
				
			lines.add(line);
			while (line.endsWith(String.valueOf(morelinesSymbol))) {
				System.out.print(multilineSymbol);
				line = sc.nextLine();
				if (line.isEmpty())
					break;
				lines.add(line);
			}
			String arg = "";
			if (lines.size() > 1) {
				for (String l : lines) {
					arg += l.replace(String.valueOf(morelinesSymbol), "");
				}
			} else {
				arg = lines.get(0);
			}
			

			String[] splittedLine = arg.split(" ");
			try {
				ShellCommand command = commands.get(splittedLine[0]);
				status = command.executeCommand(env, arg.substring(arg.indexOf(" ")+1, arg.length()));
			} catch (NullPointerException ex) {
				System.err.println("Invalid command name.");
			}
			
			
			
		}
		sc.close();
	}

}
