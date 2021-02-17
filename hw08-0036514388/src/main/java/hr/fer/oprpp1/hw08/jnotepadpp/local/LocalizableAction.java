package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Razred predstavlja akciju postavljanja odgovarajućeg prijevoda.
 * 
 * @author borna
 *
 */
public class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener; 
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ 
	 * @param lp instanca objekta koji implementira sučelje ILocalizationProvider; davatelj usluge prijevoda
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		provider = lp;
		listener = () -> this.putValue(NAME, provider.getString(this.key));
		
		String translation = lp.getString(key);
		this.putValue(NAME, translation);
		
		provider.addLocalizationListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
