package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Bazna klasa za sve cvorove. Sluzi za reprezentaciju strukturiranih dokumenata.
 * 
 * @author borna
 *
 */
public class Node {
	
	private boolean firstCall = true;
	private ArrayIndexedCollection col;
	
	/**
	 * Metoda dodaje <code>child</code> u kolekciju koristeci instancu klase ArrayIndexedCollection.
	 * 
	 * @param child Node koji se dodaje u kolekciju
	 */
	public void addChildNode(Node child) {
		if (firstCall) {
			col = new ArrayIndexedCollection();
			col.add(child);
			firstCall = false;
		} else {
			col.add(child);
		}
	}
	
	/**
	 * Metoda vraca broj izravnih cvorova djece trenutnog cvora.
	 * 
	 * @return broj djece trenutnog cvora
	 */
	public int numberOfChildren() {
		return this.col.size(); 
	}
	
	/**
	 * Metoda vraca cvor dijete sa odabrane pozicije.
	 * 
	 * @param index pozicija cvora koji se zeli dohvatiti 
	 * @return Node koji se nalazi na poziciji <code>index</code>
	 * @throws IndexOutOfBoundsException ako je zadana nedozvoljena vrijednost <code>index</code>
	 */
	public Node getChild(int index) {
		return (Node) this.col.get(index);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (obj instanceof Node) {
			Node node = (Node) obj;
			if (!this.toString().equals(node.toString())) {
				return false;
			}
		}
		return true;
	}
}
