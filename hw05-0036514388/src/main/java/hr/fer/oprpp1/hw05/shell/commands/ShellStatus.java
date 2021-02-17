package hr.fer.oprpp1.hw05.shell.commands;

/**
 * Enumeration. 
 * ShellStatus (CONTINUE or TERMINATE) depends on execution of the current command.
 * Command <code>exit</code> terminates the program.
 * 
 * @author borna
 *
 */
public enum ShellStatus {
	CONTINUE, 
	TERMINATE
}
