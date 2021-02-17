package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Klasa modelira naredbu uklanjanja jednog stanja s vrha stoga.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class PopCommand implements Command {
	
	/**
	 * Pretpostavljeni konstruktor.
	 */
	public PopCommand() {
		super();
	}
	
	/**
	 * Metoda briše jedno stanje sa vrha stoga.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
