package hr.fer.oprpp1.hw05.shell.commands;

import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Interface implemented by each shell command.
 * 
 * @author borna
 *
 */
public interface ShellCommand {
	
	/**
	 * Method is in charge of executing current command.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method returns the name of current command.
	 * 
	 * @return the name of command
	 */
	String getCommandName();
	
	/**
	 * Method returns a description (usage instructions).
	 * Since the description can span more than one line, a read-only List must be used.
	 * 
	 * @return a read-only List containing usage instructions for current command
	 */
	List<String> getCommandDescription();
}
