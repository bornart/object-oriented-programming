package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Klasa predstavlja cvor koji reprezentira naredbu koja dinamicki generira neki tekstualni ispis. Nasljeduje klasu Node.
 * 
 * @author borna
 *
 */
public class EchoNode extends Node {
	
	private Element[] elements;
	
	/**
	 * Getter.
	 * Metoda dohvaca polje <code>elements</code> tipa <code>Element</code>.
	 * 
	 * @return polje <code>elements</code>
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Konstruktor.
	 * 
	 * @param elements Polje elemenata razred <code>EchoNode</code>
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Nadjacana metoda koja vraca ispis svih elemenata instance klase <code>EchoNode</code>
	 */
	@Override
	public String toString() {
		String str = "{$= ";
		for (int i = 0; i < elements.length; i++) {
			str += elements[i].asText();
			str += " ";
		}
		str += "$}";
		return str;
	}
	
}
