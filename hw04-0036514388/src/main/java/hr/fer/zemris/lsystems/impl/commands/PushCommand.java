package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Klasa modelira naredbu dodavanja jednog stanja na vrha stoga.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class PushCommand implements Command{
	
	/**
	 * Pretpostavljeni konstruktor.
	 */
	public PushCommand() {
		super();
	}
	
	/**
	 * Metoda stanje s vrha stoga kopira i kopiju stavlja na stog.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copiedState = ctx.getCurrentState().copy(); //?
		ctx.pushState(copiedState);
	}

}
