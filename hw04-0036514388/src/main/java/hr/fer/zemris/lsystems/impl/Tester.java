package hr.fer.zemris.lsystems.impl;

/**
 * Sucelje Tester modelira objekte koji prime neki objekt te ispituju je li taj objekt prihvatljiv ili ne.
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>Tester</code>
 */
public interface Tester<T> {
	
	/**
	 * Metoda ispituje je li dani objekt prihvatljiv ili nije.
	 * 
	 * @param obj predani objekt nad kojim se vrsi provjera
	 * @return <code>true</code> ako se objekt prihvaca, a <code>false</code> u protivnom
	 */
	boolean test(T obj);
}
