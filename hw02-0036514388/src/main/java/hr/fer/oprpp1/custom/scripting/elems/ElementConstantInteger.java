package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja broj tipa Integer.
 * 
 * @author borna
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>value</code>.
	 * 
	 * @param value vrijednost varijable <code>value</code>
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>value</code>.
	 * 
	 * @return vrijednost varijable <code>value</code>
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>value</code> koja je tipa int kao String.
	 * 
	 * @return tekstualnu vrijednost varijable <code>value</code>
	 */
	@Override
	public String asText() {
		return String.valueOf(this.getValue());
	}
	
}
