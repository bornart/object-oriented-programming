package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * Klasa koja pamti trenutnu poziciju na kojoj se kornjača nalazi.
 * 
 * @author borna
 *
 */
public class TurtleState {
	
	Vector2D position;
	Vector2D direction; //jedinična duljina!
	Color color;
	double currentEffectiveLength;
	
	/**
	 * Konstruktor.
	 * 
	 * @param position
	 * @param direction
	 * @param color
	 * @param effectiveLength
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double currentEffectiveLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.currentEffectiveLength = currentEffectiveLength;
	}
	
	/**
	 * Metoda vraća novi objekt s kopijom trenutnog stanja.
	 * 
	 * @return novi objekt tipa <code>TurtleState</code> u kojem je pohranjeno trenutno stanje kornjače.
	 */
	public TurtleState copy() {
		//return new TurtleState(this.position, this.direction, this.color, this.currentEffectiveLength);
		return new TurtleState(position.copy(), direction.copy(), color, currentEffectiveLength);
	}
	
	/**
	 * Getter. Metoda vraća trenutnu poziciju na kojoj se kornjača nalazi.
	 * 
	 * @return pozicija kornjače u obliku radij-vektora tipa <code>Vector2D</code>
	 */
	public Vector2D getPosition() {
		return position;
	}
	
	/**
	 * Getter. Metoda vraća smjer u kojem kornjača gleda.
	 * 
	 * @return smjer u kojem kornjača gleda u obliku jediničnog vektora tipa <code>Vector2D</code>
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * Getter. Metoda vraća boju kornjače.
	 * 
	 * @return objekt tipa <code>Color</code> koji predstavlja boju kornjače
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Getter. Metoda vraća trenutnu efektivnu duljinu pomaka.
	 * 
	 * @return <code>double</code> vrijednost koja predstavlja trenutnu efektivnu duljinu pomaka
	 */
	public double getCurrentEffectiveLength() {
		return currentEffectiveLength;
	}
	
	/**
	 * Setter. Metoda postavlja <code>position</code> kao novu poziciju kornjače.
	 * 
	 * @param position nova pozicija kornjače
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * Setter. Metoda postavlja <code>currentEffectiveLength</code> kao novu efektivnu duljinu pomaka.
	 * 
	 * @param currentEffectiveLength nova efektiva duljina pomaka
	 */
	public void setCurrentEffectiveLength(double currentEffectiveLength) {
		this.currentEffectiveLength = currentEffectiveLength;
	}
	
	/**
	 * Setter. Metoda postavlja <code>color</code> kao novu boju kornjače.
	 * 
	 * @param color nova boja kornjače
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
