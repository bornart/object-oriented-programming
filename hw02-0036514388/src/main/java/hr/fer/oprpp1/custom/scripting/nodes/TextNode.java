package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Klasa predstavlja cvor koji reprezentira dio tekstualne informacije. Nasljeduje klasu Node.
 * 
 * @author borna
 *
 */
public class TextNode extends Node {
	
	private String text;
	
	/**
	 * Konstruktor.
	 * 
	 * @param text vrijednost tekstualnog cvora
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Getter.
	 * Metoda dohvaca vrijednost varijable <code>text</code>.
	 * 
	 * @return varijabla <code>text</code>
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Nadjacana metoda koja vraca ispis elemenata instance klase <code>TextNode</code>
	 */
	@Override 
	public String toString() {
		return this.getText();
	}
	
}
