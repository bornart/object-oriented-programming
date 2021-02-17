package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;

/**
 * Interface Eviroment is an abstraction which will be passed to each defined command.
 * The each implemented command communicates with user (reads user input and writes response) only through this interface.
 * 
 * @author borna
 *
 */
public interface Environment {
	
	/**
	 * Method reads one line from console.
	 * 
	 * @return one line from console
	 * @throws ShellIOException if reading fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method writes to console.
	 * 
	 * @param text String that needs to be written to the user (in console).
	 * @throws ShellIOException if writing fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Method writes one line to console.
	 * 
	 * @param text String that needs to be written to the user (in console).
	 * @throws ShellIOException if writing fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method returns the map of Shell Commands.
	 * 
	 * @return unmodifiable map, so that the client can not delete commands by clearing the map
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Method returns the multiline symbol.
	 * 
	 * @return Character which represents the multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method sets the multiline symbol.
	 * 
	 * @param symbol Character which represents the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method returns the prompt symbol (PROMPTSYMBOL).
	 * 
	 * @return Character which represents the prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Method sets the prompt symbol.
	 * 
	 * @param symbol Character which represents the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Method returns a special symbol that is used to inform the shell that more lines are expected (MORELINESSYMBOL).
	 * 
	 * @return Character which represents the <code>MORELINESSYMBOL</code> symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method sets the <code>MORELINESSYMBOL</code> symbol.
	 * <code>MORELINESSYMBOL</code> is a special symbol that is used to inform the shell that more lines are expected.
	 * 
	 * @param symbol Character which represents the new <code>MORELINESSYMBOL</code> symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
