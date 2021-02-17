package hr.fer.oprpp1.custom.collections;

/**
 * Sucelje Tester modelira objekte koji prime neki objekt te ispituju je li taj objekt prihvatljiv ili ne.
 * 
 * @author borna
 *
 */
public interface Tester {
	
	/**
	 * Metoda ispituje je li dani objekt prihvatljiv ili nije.
	 * 
	 * @param obj predani objekt nad kojim se vrsi provjera
	 * @return <code>true</code> ako se objekt prihvaca, a <code>false</code> u protivnom
	 */
	boolean test(Object obj);
}
