package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Metode sučelja omogućavaju prevođenje s obzirom na predani ključ.
 * 
 * @author borna
 *
 */
public interface ILocalizationProvider {
	/**
	 * Metoda za predani ključ vraća odgovarajuću vrijednost (prijevod).
	 * 
	 * @param key ključ
	 * @return odgovarajući prijevod
	 */
	String getString(String key);
	
	/**
	 * Metoda dodaje novi listener tipa <code>ILocalizationListener</code> u listu.
	 * 
	 * @param l listener; objekt tipa <code>ILocalizationListener</code>
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda uklanja listener-a iz liste.
	 * 
	 * @param l l listener; objekt tipa <code>ILocalizationListener</code>
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda dohvaća trenutno korišteni jezik.
	 * 
	 * @return trenutno korišteni jezik
	 */
	String getCurrentLanguage();
}
