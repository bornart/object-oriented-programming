package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Funkcionalno sučelje koje definira metodu zaduženu za izvođenje idućeg koraka izgradnje slike.
 * 
 * @author borna
 *
 */
@FunctionalInterface
public interface Command {
	
	/**
	 * Metoda izvodi potrebnu naredbu.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	void execute(Context ctx, Painter painter);
}
