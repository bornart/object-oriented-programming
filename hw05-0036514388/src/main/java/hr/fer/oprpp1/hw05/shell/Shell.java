package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.CatShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CharsetsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.CopyShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ExitShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HelpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.HexdumpShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.LsShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.MkdirShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.SymbolShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.TreeShellCommand;

/**
 * Class Shell is used to build environment and provide cleaner looking code.
 * Shell implements interface Environment and allows user to interact with program.
 * 
 * @author borna
 *
 */
public class Shell implements Environment{
	
	private SortedMap<String, ShellCommand> commands;
	private Character multilineSymbol;
	private Character promptSymbol;
	private Character moreLinesSymbol;
	private Scanner sc;
	
	/**
	 * Constructor sets default values for <code>multilineSymbol</code>, <code>promptSymbol</code> and <code>moreLinesSymbol</code>.
	 * Furthermore, constructor creates new scanner and builds a map of supported commands.
	 */
	public Shell() {
		multilineSymbol = '|';
		promptSymbol = '>';
		moreLinesSymbol = '\\';
		sc = new Scanner(System.in);
		commands = new TreeMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
	}
	
	@Override
	public String readLine() throws ShellIOException {
		
		if (!sc.hasNextLine()) {
			throw new ShellIOException("Console is empty.");
		}
		
		String line = sc.nextLine();
		return line;
		
	}

	@Override
	public void write(String text) throws ShellIOException {
		
		if (text == null) {
			throw new ShellIOException("Given text must not be null!");
		}
		
		System.out.printf(text);
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		
		if (text == null) {
			throw new ShellIOException("Given text must not be null!");
		}
		
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}
	
	/**
	 * Method closes the scanner when program terminates.
	 */
	public void closeScanner() {
		sc.close();
	}
}
