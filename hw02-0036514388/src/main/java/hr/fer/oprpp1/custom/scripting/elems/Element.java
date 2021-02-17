package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Bazna klasa koja sadrzi samo jednu metodu. Sluzi za reprezentaciju izraza.
 * 
 * @author borna
 *
 */
public class Element {
	
	/**
	 * Metoda u klasi Element vraca prazan string.
	 * 
	 * @return prazan string
	 */
	public String asText() {
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (obj instanceof Element) {
			Element el = (Element) obj;
			if (!this.asText().equals(el.asText())) return false;
		}
		return true;
	}
}
