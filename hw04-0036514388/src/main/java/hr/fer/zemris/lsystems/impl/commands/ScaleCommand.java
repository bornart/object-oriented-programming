package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Klasa modelira naredbu ažuriranja efektivne duljine pomaka.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class ScaleCommand implements Command {
	
	private double factor;
	
	/**
	 * Konstruktor.
	 * 
	 * @param factor faktor koji se koristi za skaliranje efektivne duljine pomaka.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Metoda ažurira efektivnu duljinu pomaka skaliranjem s danim faktorom.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		
		double newEffectiveLength = currentState.getCurrentEffectiveLength() * this.factor;
		currentState.setCurrentEffectiveLength(newEffectiveLength);
	}

}
