package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton razred. Učitava "resource bundle" za korišteni jezik i pohranjuje referencu na njega.
 * 
 * 
 * @author borna
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	private String language;
	private ResourceBundle bundle;
	
	/**
	 * Privatni konstruktor.
	 */
	private LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Metoda koja omogućava dohvat jedine instance razreda <code>LocalizationProvider</code>.
	 * 
	 * @return instanca razreda <code>LocalizationProvider</code>
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
		return bundle.getString(key);
	}
	
	/**
	 * Metoda postavlja trenutno korišteni jezik.
	 * 
	 * @param language trenutno korišteni jezik
	 */
	public void setLanguage(String language) {
		this.language = language;
		this.fire();
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}

}
