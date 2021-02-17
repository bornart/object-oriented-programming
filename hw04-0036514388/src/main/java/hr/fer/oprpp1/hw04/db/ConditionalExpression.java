package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja model potpunog kondicionalnog (uvjetnog) izraza.
 * 
 * @author borna
 *
 */
public class ConditionalExpression {
	
	private IFieldValueGetter value;
	private String literal;
	private IComparisonOperator operator;
	
	/**
	 * Konstruktor koji prima 3 reference: IFieldValueGetter strategija, String literal i IComparisonOperator strategija.
	 * 
	 * @param value referenca na IFieldValueGetter strategiju
	 * @param literal string na temelju kojega se provodi filtriranje studentskih podataka
	 * @param operator referenca na IComparisonOperator strategiju
	 */
	public ConditionalExpression(IFieldValueGetter value, IComparisonOperator operator, String literal) {
		this.value = value;
		this.literal = literal;
		this.operator = operator;
	}
	
	/**
	 * Getter. Metoda dohvaća objekt tipa <code>IFieldValueGetter</code> koji predstavlja konkretan podatak koji se želi dohvatiti iz <code>StudentRecord</code>.
	 * 
	 * @return objekt tipa <code>IFieldValueGetter</code> koji predstavlja konkretan podatak koji se želi dohvatiti
	 */
	public IFieldValueGetter getValue() {
		return value;
	}
	
	/**
	 * Getter. Metoda dohvaća string na temelju kojega se provodi filtriranje studentskih podataka.
	 * 
	 * @return tring na temelju kojega se provodi filtriranje studentskih podatak
	 */
	public String getLiteral() {
		return literal;
	}
	
	/**
	 * Getter. Metoda dohvaća objekt tipa <code>IComparisonOperator</code> koji predstavlja operator kojime se provodi usporedba podataka.
	 * 
	 * @return objekt tipa <code>IComparisonOperator</code> koji predstavlja operator
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}
	
	
}
