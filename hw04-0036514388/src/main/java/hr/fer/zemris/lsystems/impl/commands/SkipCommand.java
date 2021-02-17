package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Klasa modelira naredbu gdje kornjača mora ići bez iscrtavanja puta.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class SkipCommand implements Command {
	
	private double step;
	
	/**
	 * Konstruktor.
	 * 
	 * @param step pomak kornjače u smjeru u kojem je okrenuta (pomak za <code>step<code> efektivne duljine pomaka).
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Metoda pomiče kornjaču za <code>step<code> efektivne duljine pomaka u smjeru u kojem je okrenuta ali bez iscrtavanja linije kojom se kretala.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();
		
		double directionX = currentState.getDirection().getX();
		double directionY = currentState.getDirection().getY();
		
		double x = currentPosition.getX() + this.step * currentState.getCurrentEffectiveLength() * directionX;
		double y = currentPosition.getY() + this.step * currentState.getCurrentEffectiveLength() * directionY;
		Vector2D newPosition = new Vector2D(x, y);
		
		currentState.setPosition(newPosition);
	}


}
