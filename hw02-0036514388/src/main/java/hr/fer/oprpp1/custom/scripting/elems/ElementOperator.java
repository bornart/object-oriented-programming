package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja jedan operator.
 * 
 * @author borna
 *
 */
public class ElementOperator extends Element {
	
	private String symbol;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>symbol</code>.
	 * 
	 * @param value vrijednost varijable <code>symbol</code>
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>symbol</code>.
	 * 
	 * @return vrijednost varijable <code>symbol</code>
	 */
	public String getValue() {
		return symbol;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>symbol</code>.
	 * 
	 * @return tekstualnu vrijednost varijable <code>symbol</code>
	 */
	@Override
	public String asText() {
		return this.getValue();
	}
	
}
