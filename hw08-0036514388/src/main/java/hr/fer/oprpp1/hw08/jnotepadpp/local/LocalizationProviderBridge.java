package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Razred ima ulogu dekoratora za <code>IlocalizationProvider</code>.
 * 
 * @author borna
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private boolean connected;
	private ILocalizationProvider parent; 
	private ILocalizationListener listener; 
	private String language;
	
	/**
	 * Konstruktor.
	 * 
	 * @param provider "dekorirani" objekt tipa <code>IlocalizationProvider</code>
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		parent = provider;
		connected = false; 
		listener = () -> super.fire();
		language = parent.getCurrentLanguage(); //dodano
	}
	
	/**
	 * Spajanje; metoda dodaje listener-a na objekt tipa <code>IlocalizationProvider</code>.
	 */
	public void connect() {
		if (connected) return;
		
		//jezici (dodano):
		if (!language.equals(parent.getCurrentLanguage())) {
			language = parent.getCurrentLanguage();
			listener.localizationChanged();
		}
		
		connected = true;
		parent.addLocalizationListener(listener); 
	}
	
	/**
	 * Odspajanje; metoda uklanja listener-a sa objekta tipa <code>IlocalizationProvider</code>.
	 */
	public void disconnect() {
		if (!connected) return;
		
		//jezici (dodano):
		language = parent.getCurrentLanguage();
		
		connected = false;
		parent.removeLocalizationListener(listener); 
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}
}
