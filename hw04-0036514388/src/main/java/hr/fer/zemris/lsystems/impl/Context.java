package hr.fer.zemris.lsystems.impl;

/**
 * Klasa omogućava izvođenje postupka prikazivanja fraktala te nudi stog na koji je moguće stavljati i dohvaćati stanja kornjače.
 * 
 * @author borna
 *
 */
public class Context {
	
	ObjectStack<TurtleState> stack;
	
	/**
	 * Konstruktor koji stvara novi stog u kojem se pohranjuju stanja kornjače.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}
	
	/**
	 * Metoda vraća stanje s vrha stoga bez uklanjanja.
	 * 
	 * @return trenutno stanje kornjače (stanje koje se nalazi na vrhu stoga)
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Metoda koja na vrh stoga stavlja predano stanje.
	 * Predano stanje <code>state</code> postaje trenutnim (aktivnim) stanjem kornjače.
	 * 
	 * @param state novo trenutno stanje kornjače
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Metoda briše jedno stanje s vrha stoga. 
	 * Trenutno stanje kornjače postaje iduće stanje koje se nakon uklanjanja nalazi na vrhu stoga.
	 */
	public void popState() {
		stack.pop();
	}
}
