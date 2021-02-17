package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Class HelpShellCommand represents help command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class HelpShellCommand implements ShellCommand {
	
	/**
	 * If no arguments were given, method lists names of all supported commands.
	 * If method recieves an argument it prints name and the description of selected command (or appropriate error message).
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			for (String command : env.commands().keySet()) {
				env.writeln(command);
			}
		} else {
			arguments = arguments.trim();
			if (env.commands().get(arguments) != null) {
				List<String> description = env.commands().get(arguments).getCommandDescription();
				for (String d : description) {
					env.writeln(d);
				}
			} else {
				env.writeln("No such command.");
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("If started with no arguments, command help must list names of all supported commands.");
		desc.add("If started with single argument, it must print name and the description of selected command (or appropriate error message).");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
