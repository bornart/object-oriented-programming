package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Klasa predstavlja cvor koji reprezentira jedan for-loop konstrukt. Nasljeduje klasu Node.
 * 
 * @author borna
 *
 */
public class ForLoopNode extends Node {
	
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	
	/**
	 * Konstruktor.
	 * 
	 * @param variable
	 * @param startExpression 
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter.
	 * Metoda dohvaca vrijednost varijable <code>variable</code>.
	 * 
	 * @return varijabla <code>variable</code>
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Getter.
	 * Metoda dohvaca vrijednost varijable <code>startExpression</code>.
	 * 
	 * @return varijabla <code>startExpression</code>
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Getter.
	 * Metoda dohvaca vrijednost varijable <code>endExpression</code>.
	 * 
	 * @return varijabla <code>endExpression</code>
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Getter.
	 * Metoda dohvaca vrijednost varijable <code>stepExpression</code>. Varijabla moze biti <code>null</code>.
	 * 
	 * @return varijabla <code>stepExpression</code>
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Nadjacana metoda koja vraca ispis svih elemenata instance klase <code>ForLoopNode</code> zajedno sa svom eventualnom djecom-cvorovima.
	 */
	@Override
	public String toString() {
		String str = "{$ FOR ";
		str += variable.asText() + " ";
		str += startExpression.asText() + " ";
		str += endExpression.asText() + " ";
		str += stepExpression.asText() + " $} ";
		
		for (int i = 0; i < this.numberOfChildren(); i++) {
			str += getChild(i).toString();
		}
		str += "{$END$}";
		return str;
	}

}
