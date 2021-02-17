package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa koja nasljeduje klasu RuntimeException i predstavlja novostvorenu iznimku koja se javlja u sljedecim slucajevima:
 * broj koji nije prikaziv tipom Long,
 * korisnik nakon sto lexer vrati token tipa EOF zatrazi generiranje sljedeceg tokena,
 * koristenje nedopustenih escape sekvenci.
 * 
 * @author borna
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Pretpostavljeni <code>serial version ID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prazni konstruktor.
	 */
	public LexerException() {}
	
	/**
	 * Konstruktor koji prima poruku i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima uzrok iznimke i prosljeduje ju konstruktoru bazne klase.
	 * 
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Konstruktor koji prima poruku i uzrok iznimke te ih prosljeduje konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slucaju generiranja pogreske. Prosljeduje se konstruktoru bazne klase
	 * @param cause uzrok iznimke koji se prosljeduje konstruktoru bazne klase
	 */
	public LexerException(String message, Throwable cause) {
		super (message, cause);
	}
}
