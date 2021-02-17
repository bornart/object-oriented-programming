package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Klasa predstavlja cvor koji reprezentira citavi dokument. Nasljeduje klasu Node.
 * 
 * @author borna
 *
 */
public class DocumentNode extends Node {
	
	/**
	 * Nadjacana metoda koja vraca ispis svih cvorova-djece korijenskog cvora.
	 */
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.numberOfChildren(); i++) {
			str += getChild(i).toString();
		}
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (obj instanceof DocumentNode) {
			DocumentNode node = (DocumentNode) obj;
			if (this.numberOfChildren() != node.numberOfChildren()) return false;
			for (int i = 0; i < this.numberOfChildren(); i++) {
				if (!this.getChild(i).equals(node.getChild(i))) return false;
			}
		}
		return true;
	}
}
