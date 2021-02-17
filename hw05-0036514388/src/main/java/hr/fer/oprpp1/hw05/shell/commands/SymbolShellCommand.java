package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Class SymbolShellCommand represents symbol command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class SymbolShellCommand implements ShellCommand {
	
	/**
	 * Method is used to get or change the charecter which represents PROMPTSYMBOL, MORELINESSYMBOL or MULTILINESYMBOL.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] elems = arguments.split(" ");
		
		if (elems.length == 1) {
			if (elems[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if (elems[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			} else if (elems[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else {
				env.writeln("Illegal argument for SYMBOL command!");
				return ShellStatus.CONTINUE;
			}
		} else if (elems.length == 2) {
			Character c;
			if (elems[1].length() == 1) {
				c = elems[1].charAt(0);
			} else {
				env.writeln("Illegal argument for SYMBOL command!");
				return ShellStatus.CONTINUE;
			}
			
			if (elems[0].equals("PROMPT")) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "'" + " to '" + elems[1] + "'");
				env.setPromptSymbol(c);
			} else if (elems[0].equals("MORELINES")) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "'" + " to '" + elems[1] + "'");
				env.setMorelinesSymbol(c);
			} else if (elems[0].equals("MULTILINE")) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "'" + " to '" + elems[1] + "'");
				env.setMultilineSymbol(c);
			} else {
				env.writeln("Illegal argument for SYMBOL command!");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Illegal argument for SYMBOL command!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command symbol can be used to get charecter which represents PROMPTSYMBOL, MORELINESSYMBOL or MULTILINESYMBOL.");
		desc.add("Command symbol can be used to change PROMPTSYMBOL/MORELINESSYMBOL/MULTILINESYMBOL.");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
