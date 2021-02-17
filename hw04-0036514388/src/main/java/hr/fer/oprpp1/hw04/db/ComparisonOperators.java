package hr.fer.oprpp1.hw04.db;

/**
 * Klasa koja predstavlja implementaciju operatora usporedbe.
 * 
 * @author borna
 *
 */
public class ComparisonOperators {
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "manje" između dva stringa.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "manje ili jednako" između dva stringa.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "više" između dva stringa.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "više ili jednako" između dva stringa.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "jednako" između dva stringa.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja predstavlja operator usporedbe "različito" između dva stringa.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
	
	/**
	 * Konstanta tipa <code>IComparisonOperator</code> koja omogućuje usporedbu dva stringa.
	 * Usporedba stringova moguća je i korištenjem "wildcard" znaka * koji zamjenjuje bilo koji drugi znak.
	 * Wildcard znak * se smije pojaviti samo jednom unutar stringa. 
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		String s = v2;
		if ((s.length() - s.replace("*", "").length()) > 1) {
			return false;
		}
		
		String regexPattern = v2.replace("*", "[0-9a-zA-ZšđčćžŽĆČŠĐ \\-]*");
		return v1.matches(regexPattern);
	};
}
