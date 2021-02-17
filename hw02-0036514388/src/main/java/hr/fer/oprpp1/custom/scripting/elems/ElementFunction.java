package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja matematicku funkciju.
 * 
 * @author borna
 *
 */
public class ElementFunction extends Element{

	private String name;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>name</code>.
	 * 
	 * @param value vrijednost varijable <code>name</code>
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>name</code>.
	 * 
	 * @return vrijednost varijable <code>name</code>
	 */
	public String getValue() {
		return name;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>name</code>.
	 * 
	 * @return tekstualnu vrijednost varijable <code>name</code>
	 */
	@Override
	public String asText() {
		return "@" + this.getValue();
	}
	
}
