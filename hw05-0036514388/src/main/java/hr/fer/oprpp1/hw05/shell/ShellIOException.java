package hr.fer.oprpp1.hw05.shell;

/**
 * Class extends RuntimeException class and represents exception generated whenever a mistake occures during the process of reading user input or writing response. 
 * 
 * @author borna
 *
 */
public class ShellIOException extends RuntimeException {
	
	/**
	 * Default <code>serial version ID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty constructor.
	 */
	public ShellIOException() {}
	
	/**
	 * Constructor recieves a message and forwards it to the base-class constructor.
	 * 
	 * @param message message presented to the user whenever ShellIOException occures.
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * Constructor recieves Throwable object (which represents the cause of the exception) and forwards it to the base-class constructor.
	 * 
	 * @param cause Throwable object which represents the cause of the exception
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor recieves a message and Throwable object (which represents the cause of the exception) and forwards them to the base-class constructor.
	 * 
	 * @param message message presented to the user whenever ShellIOException occures.
	 * @param cause Throwable object which represents the cause of the exception
	 */
	public ShellIOException(String message, Throwable cause) {
		super (message, cause);
	}

}
