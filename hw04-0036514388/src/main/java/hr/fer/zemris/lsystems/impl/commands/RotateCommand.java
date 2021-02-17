package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Klasa modelira naredbu modificiranja vektora smjera gledanja kornjače.
 * Implementira sučelje <code>Command</code>.
 * 
 * @author borna
 *
 */
public class RotateCommand implements Command{
	
	private double angle;
	
	/**
	 * Konstruktor.
	 * 
	 * @param angle kut rotacije smjera gledanja kornjače
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Metoda u trenutnom stanju modificira vektor smjera gledanja kornjače tako što ga rotira za zadani kut.
	 * 
	 * @param ctx trenutni kontekst
	 * @param painter objekt za crtanje linija tipa sučelje <code>Painter</code>
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(Math.toRadians(this.angle));
	}

}
