package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Class MkdirShellCommand represents mkdir command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class MkdirShellCommand implements ShellCommand {
	
	/**
	 * Method takes a single argument: directory name, and creates the appropriate directory structure.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		boolean createDirectory = new File(arguments).mkdirs();
		if (createDirectory)  {
			env.writeln("Directory created!");
		} else {
			env.writeln("Error occured.");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
