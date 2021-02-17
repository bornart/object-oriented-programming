package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Klasa koja nasljeduje klasu RuntimeException i predstavlja novostvorenu iznimku koja se javlja ukoliko se pojavi bilokoja iznimka u procesu parsiranja.
 * @author borna
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Pretpostavljeni <code>serial version ID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prazni konstruktor.
	 */
	public SmartScriptParserException() {}
	
	/**
	 * Konstruktor koji prima poruku i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima uzrok iznimke i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Konstruktor koji prima poruku i uzrok iznimke te ih prosljeduje konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super (message, cause);
	}
}
