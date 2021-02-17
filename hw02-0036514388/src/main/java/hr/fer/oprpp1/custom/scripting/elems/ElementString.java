package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja jedan String.
 * 
 * @author borna
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>value</code>.
	 * 
	 * @param value vrijednost varijable <code>value</code>
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>value</code>.
	 * 
	 * @return vrijednost varijable <code>value</code>
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>value</code>.
	 * 
	 * @return tekstualnu vrijednost varijable <code>value</code>
	 */
	@Override
	public String asText() {
		return this.getValue();
	}
	
}	
