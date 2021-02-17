package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Klasa nasljeduje baznu klasu <code>Element</code>. Predstavlja varijablu.
 * 
 * @author borna
 *
 */
public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * Konstruktor koji prima jedan parametar <code>name</code>.
	 * 
	 * @param name vrijednost varijable <code>name</code>
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Getter.
	 * Metoda vraca vrijednost varijable <code>name</code>.
	 * 
	 * @return tekstualnu vrijednost varijable <code>name</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metoda vraca vrijednost varijable <code>name</code>.
	 * 
	 * @return tekstualnu vrijednost varijable <code>name</code>
	 */
	@Override
	public String asText() {
		return this.getName();
	}
	
}
