package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Class CharsetsShellCommand represents charsets command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	
	/**
	 * Method takes no arguments and lists names of supported charsets for users Java platform.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			 Map<String, Charset> charsets = Charset.availableCharsets();
			 
			 Iterator<Charset> it = charsets.values().iterator();
			 
			 while (it.hasNext()) {
				 Charset c = (Charset)it.next();
				 env.writeln(c.displayName());
			 }
		} else {
			env.writeln("Command charsets takes no arguments!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command charsets takes no arguments and lists names of supported charsets for your Java platform.");
		desc.add("A single charset name is written per line.");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
