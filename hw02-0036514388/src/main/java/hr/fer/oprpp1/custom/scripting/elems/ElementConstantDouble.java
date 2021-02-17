package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja broj tipa Double.
 * 
 * @author borna
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>value</code>.
	 * 
	 * @param value vrijednost varijable <code>value</code>
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>value</code>.
	 * 
	 * @return vrijednost varijable <code>value</code>
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>value</code> koja je tipa double kao String.
	 * 
	 * @return tekstualnu vrijednost varijable <code>value</code>
	 */
	@Override
	public String asText() {
		return String.valueOf(this.getValue());
	}
	
}
