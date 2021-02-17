package hr.fer.zemris.lsystems.impl;

/**
 * Klasa koja nasljeduje klasu RuntimeException i predstavlja novostvorenu iznimku koja se javlja ukoliko se pokusaju obaviti operacije pop() ili peek() nad praznim stogom.
 * 
 * @author borna
 *
 */
public class EmptyStackException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prazni konstruktor.
	 */
	public EmptyStackException() {}
	
	/**
	 * Konstruktor koji prima poruku i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima uzrok iznimke i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Konstruktor koji prima poruku i uzrok iznimke te ih prosljeduje konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public EmptyStackException(String message, Throwable cause) {
		super (message, cause);
	}
}
