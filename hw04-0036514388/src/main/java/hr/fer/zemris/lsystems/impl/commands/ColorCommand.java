package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Klasa modelira naredbu ažuriranja boje kornjače.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class ColorCommand implements Command {
	
	private Color color;
	
	/**
	 * Konstruktor.
	 * 
	 * @param color boja kornjače
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Metoda u trenutno stanje kornjače zapisuje predanu boju.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setColor(this.color);
	}

}
