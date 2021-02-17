package hr.fer.zemris.java.gui.layouts;

/**
 * Iznimka izvedena iz razreda <code>RuntimeException</code> koja se javlja u slučajevima korištenja nedozvoljenih ograničenja popisanih u razredu <code>CalcLayout</code>.
 * 
 * @author borna
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Pretpostavljeni <code>serial version ID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prazni konstruktor.
	 */
	public CalcLayoutException() {}
	
	/**
	 * Konstruktor koji prima poruku i prosljeđuje ju konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slučaju generiranja iznimke. Prosljeđuje se konstruktoru bazne klase
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * Konstruktor koji prima objekt tipa <code>Throwable</code> (uzrok iznimke) i prosljeđuje ga konstruktoru bazne klase.
	 * 
	 * @param cause objekt tipa <code>Throwable</code> koji predstavlja uzrok iznimke, a prosljeđuje konstruktoru bazne klase
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Konstruktor koji prima poruku i objekt tipa <code>Throwable</code> (koji predstavlja uzrok iznimke) te ih prosljeđuje konstruktoru bazne klase.
	 * 
	 * @param message poruka koja se prikazuje korisniku u slučaju generiranja iznimke
	 * @param cause objekt tipa <code>Throwable</code> koji predstavlja uzrok iznimke
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super (message, cause);
	}
}
