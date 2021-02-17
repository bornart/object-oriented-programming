package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktni razred koji implementira sučelje <code>ILocalizationProvider</code> i dodaje mogućnosti dodavanja, uklanjanja i informiranja listener-a.
 * 
 * @author borna
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	private List<ILocalizationListener> listeners;
	
	/**
	 * Konstruktor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
