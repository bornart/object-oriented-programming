package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Funkcijsko sučelje čija se metoda <code>localizationChanged</code> poziva pri promjeni korištenog jezika.
 * 
 * @author borna
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Metodu poziva Subjekt pri promjeni korištenog jezika.
	 */
	void localizationChanged();
}
